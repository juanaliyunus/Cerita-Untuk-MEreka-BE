package com.example.cum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrphanagesNeedRequest {
    @NotBlank
    @JsonProperty("user_id")
    private String userId;
    private Integer quantity;
    @JsonProperty("book_name")
    private String bookName;
//    @JsonProperty("orphanges_id")
//    private String orphanagesId;
}
