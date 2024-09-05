package com.example.cum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DonorCreateRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String address;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotBlank
    @Size(min = 1, max = 100)
    @JsonProperty("full_name")
    private String fullName;
}
