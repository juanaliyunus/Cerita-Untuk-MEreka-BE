package com.example.cum.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DonorResponse {
    private String id;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String address;
    @JsonProperty("fullname")
    private String fullname;
    private String avatar;
}
