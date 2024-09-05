package com.example.cum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrphanagesCreateRequest {
     @NotBlank
     @Email
     private String email;
     @NotBlank
     private String address;
     @JsonProperty("phone_number")
     @NotBlank
     private String phoneNumber;
     @NotBlank
     private String name;
     private String description;
     @JsonProperty("web_url")
     private String webUrl;
}
