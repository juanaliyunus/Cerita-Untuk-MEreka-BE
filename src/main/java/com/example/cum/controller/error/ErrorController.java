package com.example.cum.controller.error;

import com.example.cum.dto.response.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ErrorController {
    // Handle exceptions here
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> handleException(ResponseStatusException ex){
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.valueOf(ex.getStatusCode().value()))
                .message(ex.getReason())
                .build();
        return ResponseEntity.badRequest()
                .body(response);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> constraint(ConstraintViolationException e) {
        return ResponseEntity.badRequest()
                .body(WebResponse.<String>builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(e.getMessage())
                        .build());
    }
}
