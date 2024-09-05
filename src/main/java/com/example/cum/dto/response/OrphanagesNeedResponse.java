package com.example.cum.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrphanagesNeedResponse {
    @NotBlank
    private String id;
    private Integer quantity;
    @JsonProperty("book_name")
    @NotBlank
    private String bookName;
    @JsonProperty("orphanges_id")
    @NotBlank
    private String orphanagesId;
    private String status;
    @JsonProperty("target_quantity")
    private Integer targetQuantity;
    @JsonProperty("created_at")
    private Long createdAt;
}
