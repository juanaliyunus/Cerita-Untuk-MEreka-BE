package com.example.cum.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StockResponse {
    private String id;
    @JsonProperty("bookName")
    private String bookName;
    @JsonProperty("quantity_available")
    private int quantityAvailable;
    @JsonProperty("orphanages_id")
    private String orphanagesId;
    private Long createdAt;
}
