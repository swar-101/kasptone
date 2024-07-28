package com.example.user_auth_service.service;

import com.example.user_auth_service.dto.UserDTO;
import com.example.user_auth_service.entity.User;
import com.example.user_auth_service.mapper.UserMapper;
import com.example.user_auth_service.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO getUserDetails(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        log.info("[UserService][getUser] The user details for {} retrieved from database.", id);

        return userMapper.mapToDto(user);
    }
}