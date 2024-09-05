package com.example.cum.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateFeedbackRequest {
    @NotBlank
    @JsonIgnore
    private String id;
    @NotBlank
    @JsonProperty("donor_id")
    private String donorId;
    @NotBlank
    @JsonProperty("orphanages_id")
    private String orphanagesId;
    @NotBlank
    @JsonProperty("feedback_text")
    private String feedbackText;
}
