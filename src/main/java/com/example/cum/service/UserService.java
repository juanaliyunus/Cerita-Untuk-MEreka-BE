package com.example.cum.service;


import com.example.cum.dto.response.UserResponse;
import com.example.cum.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserById(String id);
    UserResponse getUser(String id);
}
