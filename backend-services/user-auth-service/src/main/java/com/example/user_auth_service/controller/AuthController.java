package com.example.user_auth_service.controller;

import com.example.user_auth_service.dto.LoginRequestDTO;
import com.example.user_auth_service.dto.SignupRequestDTO;
import com.example.user_auth_service.dto.UserDTO;
import com.example.user_auth_service.dto.ValidationRequestDTO;
import com.example.user_auth_service.entity.User;
import com.example.user_auth_service.mapper.UserMapper;
import com.example.user_auth_service.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    @Autowired
    private AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignupRequestDTO signupRequestDTO) {
        log.info("[AuthController][signUp] signUp requestDTO: {}", signupRequestDTO);

        User user = authService.signUp(signupRequestDTO.getEmail(), signupRequestDTO.getPassword());

        return new ResponseEntity<>(userMapper.mapToDto(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            Pair<User, MultiValueMap<String, String>> response = authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

            if (response == null) {
                throw new IllegalArgumentException("Invalid email or password");
            }

            return new ResponseEntity<>(userMapper.mapToDto(response.a), response.b, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public void logout() {

    }

    @PostMapping("/forgotPassword")
    public void forgetPassword() {

    }

    @PostMapping
    public void validateToken(@RequestBody ValidationRequestDTO validationRequestDTO) {
        Boolean result = authService.validateToken(validationRequestDTO.getToken(), validationRequestDTO.getId());
        if (Boolean.FALSE.equals(result)) {
            throw new IllegalArgumentException("Invalid token");
        }
    }
}