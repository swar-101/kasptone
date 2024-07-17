package com.example.user_auth_service.service;

import com.example.user_auth_service.entity.Session;
import com.example.user_auth_service.entity.SessionStatus;
import com.example.user_auth_service.entity.User;
import com.example.user_auth_service.repository.SessionRepository;
import com.example.user_auth_service.repository.UserRepository;
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

@Log4j2
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionRepository sessionRepository;
    private final SecretKey secretKey;

    @Autowired
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                       SessionRepository sessionRepository, SecretKey secretKey) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionRepository = sessionRepository;
        this.secretKey = secretKey;
    }

    public User signUp(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent())
            return userOptional.get();

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);

        return user;
    }

    public Pair<User, MultiValueMap<String, String>> login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            return null; // Custom exception

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
            return null; // Custom exception

        Map<String, Object> jwtData = new HashMap<>();
        jwtData.put("email", user.getEmail());
        jwtData.put("roles", user.getRoles());

        Long nowInMillis = System.currentTimeMillis();
        jwtData.put("iat", nowInMillis);
        jwtData.put("exp", nowInMillis + 1000000);
        String token = Jwts.builder().claims(jwtData).signWith(secretKey).compact();

        Session session = new Session();
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE, token);

        return new Pair<>(user, headers);
    }

    public Boolean validateToken(String token, Long userId) {
        Optional<Session> optionalSession = sessionRepository.findByToken(token);
        if (optionalSession.isEmpty())
            return false;

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long expiryInEpoch = (Long)claims.get("exp");
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
}