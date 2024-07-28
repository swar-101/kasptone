package com.example.user_auth_service.service;

import com.example.user_auth_service.client.KafkaProducerClient;
import com.example.user_auth_service.dto.MessageDTO;
import com.example.user_auth_service.entity.Session;
import com.example.user_auth_service.entity.SessionStatus;
import com.example.user_auth_service.entity.User;
import com.example.user_auth_service.exception.AuthenticationException;
import com.example.user_auth_service.exception.UserNotFoundException;
import com.example.user_auth_service.repository.SessionRepository;
import com.example.user_auth_service.repository.UserRepository;
import com.example.user_auth_service.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.user_auth_service.utils.Constants.*;

@Log4j2
@Service
public class NxtAuthService implements AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretKey secretKey;
    private final KafkaProducerClient kafkaProducerClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public NxtAuthService(UserRepository userRepository, SessionRepository sessionRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder, SecretKey secretKey,
                          KafkaProducerClient kafkaProducerClient, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.secretKey = secretKey;
        this.kafkaProducerClient = kafkaProducerClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public User signUp(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent())
            return userOptional.get();

        User user = saveUser(email, password);

//        kafkaProducerClient.sendMessage(KAFKA_TOPIC_SIGNUP, prepareWelcomeMessage(email));

        return user;
    }

    @Override
    public Pair<User, MultiValueMap<String, String>> login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new UserNotFoundException("User Not Found.");
        User user = optionalUser.get();

        if (!isPasswordMatching(password, user.getPassword()))
            throw new AuthenticationException("Password doesn't match.");

        saveSession(user);
        String token = createJwtToken(user);

        return new Pair<>(user, createCookieHeaderWithToken(token));
    }

    @Override
    public Boolean validateToken(String token, Long userId) {
        Optional<Session> optionalSession = sessionRepository.findByToken(token);
        if (optionalSession.isEmpty())
            return false;

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long expiryInEpoch = (Long) claims.get("exp");
        Long currentTime = System.currentTimeMillis();

        if (currentTime > expiryInEpoch)
            // add logic for turning the session status as expired and persist in DB
            return false;

        Optional<User> optionalUser = userRepository.findById(userId);
        String email = optionalUser.get().getEmail();

        if (!email.equals(claims.get("email")))
            // add logic for throwing an exception where this email is not found.
            return false;

        return true;
    }

    private String createJwtToken(User user) {
        Map<String, Object> jwtData = new HashMap<>();
        jwtData.put(EMAIL, user.getEmail());
        jwtData.put(ROLES, user.getRoles());
        jwtData.put(ISSUED_AT, System.currentTimeMillis());
        jwtData.put(EXPIRES_AT, System.currentTimeMillis() + delayInMinutes(15));

        return Jwts.builder().claims(jwtData).signWith(secretKey).compact();
    }

    private MultiValueMap<String, String> createCookieHeaderWithToken(String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE, token);

        return headers;
    }

    private void saveSession(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);
    }

    private User saveUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);

        return user;
    }

    private String prepareWelcomeMessage(String email) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(email);
        messageDTO.setFrom(NXT_WELCOME_EMAIL_ID);
        messageDTO.setSubject(NXT_WELCOME_SUBJECT);
        messageDTO.setBody(NXT_WELCOME_MSG_BODY);
        try {
            return objectMapper.writeValueAsString(messageDTO);
        } catch (JsonProcessingException e) {
            log.error("[][] Sending message to Email Service failed:", e);
            return null; // custom exception
        }
    }

    boolean isPasswordMatching(String inputPassword, String storedPassword) {
        return bCryptPasswordEncoder.matches(inputPassword, storedPassword);
    }

    long delayInMinutes(long minutes) {
        return minutes * 60 * 1000;
    }
}