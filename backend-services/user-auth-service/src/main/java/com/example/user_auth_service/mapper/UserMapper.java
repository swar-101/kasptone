package com.example.user_auth_service.mapper;

import com.example.user_auth_service.dto.UserDTO;
import com.example.user_auth_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO mapToDto(User user);
}