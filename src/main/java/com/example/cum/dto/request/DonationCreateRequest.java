package com.example.cum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DonationCreateRequest {
    @JsonProperty("quantity_donated")
    @Min(1)
    private int quantityDonated;
    @JsonProperty("book_name")
    @NotBlank
    private String bookName;
    @JsonProperty("orphanages_id")
    @NotBlank
    private String orphanagesId;
}
