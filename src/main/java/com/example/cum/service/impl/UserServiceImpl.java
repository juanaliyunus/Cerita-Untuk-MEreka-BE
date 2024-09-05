package com.example.cum.service.impl;

import com.example.cum.dto.response.UserResponse;
import com.example.cum.entity.AppUser;
import com.example.cum.repository.UserRepository;
import com.example.cum.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public AppUser loadUserById(String id) {
        System.out.println(id);
        var user = userRepository.findById(id).orElseThrow(() -> {
            return new UsernameNotFoundException("Invalid credential user");
        });
        return AppUser.builder()
                .Id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    @Override
    public UserResponse getUser(String id) {
        var user = userRepository.findById(id).orElseThrow(() -> {
            return new UsernameNotFoundException("Invalid credential user");
        });
        return
                UserResponse.builder()
                       .username(user.getUsername())
                       .role(user.getRole())
                       .build()
                ;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Invalid username or password");
            return new UsernameNotFoundException("Invalid credential user");
        });
        return AppUser.builder()
                .Id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
