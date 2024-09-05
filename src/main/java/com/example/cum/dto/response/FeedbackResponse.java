package com.example.cum.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedbackResponse {
    private String id;
    @JsonProperty("donor_id")
    private String donorId;
    @JsonProperty("orphanages_id")
    private String orphanagesId;
    @JsonProperty("feedback_text")
    private String feedbackText;
    @JsonProperty("updated_at")
    private Long updatedAt;
    @JsonProperty("created_at")
    private Long createdAt;
}
