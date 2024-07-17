package com.example.user_auth_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Session extends BaseModel {
    private String token;

    private Date expiryDate;

    @ManyToOne
    private User user;

    @Enumerated
    private SessionStatus sessionStatus;
}
