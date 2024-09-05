package com.example.cum.dto.response;

import com.example.cum.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DonationResponse {
    private String id;
    @JsonProperty("quantity_donated")
    private int quantityDonated;
    @JsonProperty("book_name")
    private String bookName;
    @JsonProperty("orphanages_id")
    private String orphanagesId;
    private String status;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;
}
