package com.example.cum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStockRequest {
    @Min(1)
    private int quantity;
    @NotBlank
    @JsonProperty("orphanage_id")
    private String orphanageId;
    @NotBlank
    private String bookName;
}
