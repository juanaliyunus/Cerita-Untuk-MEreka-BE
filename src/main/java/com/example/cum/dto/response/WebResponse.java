package com.example.cum.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Builder
@Data
public class WebResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
}
