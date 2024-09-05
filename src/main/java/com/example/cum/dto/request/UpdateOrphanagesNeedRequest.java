package com.example.cum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrphanagesNeedRequest {
    @NotBlank
    private String id;
    private Integer quantity;
    @JsonProperty("book_name")
    private String bookName;
}
