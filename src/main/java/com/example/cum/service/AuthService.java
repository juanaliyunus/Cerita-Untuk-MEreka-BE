package com.example.cum.service;

import com.example.cum.dto.request.LoginRequest;
import com.example.cum.dto.request.RegisterDonorRequest;
import com.example.cum.dto.request.RegisterOrphanagesRequest;
import com.example.cum.dto.response.LoginResponse;

public interface AuthService {
    void registerDonor(RegisterDonorRequest request);
    void registerOrphanages(RegisterOrphanagesRequest request);

    LoginResponse login(LoginRequest request);
}
