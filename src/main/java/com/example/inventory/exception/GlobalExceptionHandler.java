package com.example.inventory.exception;

import com.example.inventory.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.badRequest().body(ApiResponse.failure(error));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeErrors(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherErrors(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(ApiResponse.failure("Internal server error: " + ex.getMessage()));
    }
}
