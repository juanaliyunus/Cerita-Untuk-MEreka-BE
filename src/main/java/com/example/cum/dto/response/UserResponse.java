package com.example.cum.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {
    private String username;
    private String role;
}
