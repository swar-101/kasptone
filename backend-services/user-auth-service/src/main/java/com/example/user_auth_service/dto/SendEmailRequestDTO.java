package com.example.user_auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailRequestDTO {
    private String to;
    private String from;
    private String subject;
    private String body;
}
