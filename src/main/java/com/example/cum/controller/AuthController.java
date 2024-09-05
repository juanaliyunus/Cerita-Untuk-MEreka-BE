package com.example.cum.controller;

import com.example.cum.dto.request.LoginRequest;
import com.example.cum.dto.request.RegisterDonorRequest;
import com.example.cum.dto.request.RegisterOrphanagesRequest;
import com.example.cum.dto.response.LoginResponse;
import com.example.cum.dto.response.WebResponse;
import com.example.cum.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public WebResponse<LoginResponse> auth(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return WebResponse.<LoginResponse>builder()
                .status(HttpStatus.OK)
                .message("Login successful")
                .data(loginResponse)
                .build();
    }

    @PostMapping("/register/donor")
    public WebResponse<Void> registerDonor(@RequestBody RegisterDonorRequest registerDonorRequest) {
        authService.registerDonor(registerDonorRequest);
        return WebResponse.<Void>builder()
               .status(HttpStatus.OK)
               .message("Donor registered successfully")
               .build();
    }

    @PostMapping("/register/orphanages")
    public WebResponse<Void> registerOrphanages(@RequestBody RegisterOrphanagesRequest registerOrphanagesRequest) {
        authService.registerOrphanages(registerOrphanagesRequest);
        return WebResponse.<Void>builder()
               .status(HttpStatus.OK)
               .message("Orphanages registered successfully")
               .build();
    }
}
