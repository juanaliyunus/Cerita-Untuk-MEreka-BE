package com.example.cum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrphanagesUpdateRequest {
     private String email;
     private String address;
     @JsonProperty("phone_number")
     private String phoneNumber;
     private String name;
     private String description;
     @JsonProperty("web_url")
     private String webUrl;
}
