package com.example.cum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDonorRequest {
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 8, max = 50)
    private String password;
    @NotNull
    @JsonProperty("donor")
    private DonorCreateRequest donor;
}
