package com.example.user_auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private String from;
    private String to;
    private String subject;
    private String body;
}