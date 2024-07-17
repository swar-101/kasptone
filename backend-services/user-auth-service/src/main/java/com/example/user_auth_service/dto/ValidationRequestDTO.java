package com.example.user_auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationRequestDTO {
    private Long id;
    private String token;
}