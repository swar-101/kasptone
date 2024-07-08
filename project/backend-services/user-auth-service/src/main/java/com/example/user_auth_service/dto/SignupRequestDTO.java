package com.example.user_auth_service.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private String email;
    private String password;
}