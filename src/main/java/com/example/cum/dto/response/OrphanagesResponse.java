package com.example.cum.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrphanagesResponse {
    private String id;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String address;
    @JsonProperty("name")
    private String name;
    private String description;
    @JsonProperty("web_url")
    private String webUrl;
    private String avatar;
    private String status;
}