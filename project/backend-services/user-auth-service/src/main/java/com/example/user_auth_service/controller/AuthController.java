package com.example.user_auth_service.controller;

import com.example.user_auth_service.dto.SignupRequestDTO;
import com.example.user_auth_service.dto.UserDTO;
import com.example.user_auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthService authService;

    @Autowired
    private AuthController(AuthService authService) {
        this.authService = authService;
    }
//
//    @PostMapping("/auth/signup")
//    public ResponseEntity<UserDTO> signUp(@RequestBody SignupRequestDTO signupRequestDTO) {
//
//    }

}
