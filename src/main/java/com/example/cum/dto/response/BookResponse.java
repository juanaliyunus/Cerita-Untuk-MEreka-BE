package com.example.cum.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private String id;
    private String name;
    private String genre;
    private String year;
    @JsonProperty("cover_url")
    private String coverUrl;
}
