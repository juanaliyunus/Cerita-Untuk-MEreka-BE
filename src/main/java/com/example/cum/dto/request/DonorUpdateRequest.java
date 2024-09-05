package com.example.cum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DonorUpdateRequest {
    private String email;
    private String address;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @Size(min = 1, max = 100)
    @JsonProperty("fullname")
    private String fullName;
}
