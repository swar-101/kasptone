package com.example.product_catalog_service.dto;

import com.example.product_catalog_service.entity.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private String email;
    private Set<Role> roles = new HashSet<>();
}