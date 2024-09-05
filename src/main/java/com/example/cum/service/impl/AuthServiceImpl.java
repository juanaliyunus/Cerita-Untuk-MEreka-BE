package com.example.cum.service.impl;

import com.example.cum.dto.request.LoginRequest;
import com.example.cum.dto.request.RegisterDonorRequest;
import com.example.cum.dto.request.RegisterOrphanagesRequest;
import com.example.cum.dto.response.LoginResponse;
import com.example.cum.entity.AppUser;
import com.example.cum.entity.User;
import com.example.cum.repository.DonorRepository;
import com.example.cum.repository.UserRepository;
import com.example.cum.security.JwtUtil;
import com.example.cum.service.AuthService;
import com.example.cum.service.DonorService;
import com.example.cum.service.OrphanagesService;
import com.example.cum.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final DonorService donorService;
    private final OrphanagesService orphanagesService;

    @Override
    @Transactional
    public void registerDonor(RegisterDonorRequest request) {
        validationService.validate(request);

        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        User usernew = User.builder()
               .username(request.getUsername())
               .password(passwordEncoder.encode(request.getPassword()))
               .role("ROLE_DONOR")
                .createdAt(System.currentTimeMillis())
               .build();
        userRepository.save(usernew);
        donorService.createDonor(usernew,request.getDonor());
    }

    @Override
    @Transactional
    public void registerOrphanages(RegisterOrphanagesRequest request) {
        validationService.validate(request);

        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        User usernew = User.builder()
               .username(request.getUsername())
               .password(passwordEncoder.encode(request.getPassword()))
               .role("ROLE_ORPHANAGES")
               .createdAt(System.currentTimeMillis())
               .build();
        userRepository.save(usernew);
        orphanagesService.createOrphanages(usernew,request.getOrphanages());
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        validationService.validate(request);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);
        return LoginResponse.builder()
                .username(appUser.getUsername())
                .role(appUser.getRole())
                .token(token)
                .build();
    }
}
