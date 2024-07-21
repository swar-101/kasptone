package com.example.user_auth_service.service;

import com.example.user_auth_service.entity.User;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;

public interface AuthService {
    User signUp(String email, String password);

    Pair<User, MultiValueMap<String, String>> login(String email, String password);

    Boolean validateToken(String token, Long userId);
}