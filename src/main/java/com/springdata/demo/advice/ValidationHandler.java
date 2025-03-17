package com.springdata.demo.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleOutput(MethodArgumentNotValidException ex) {
        Map<String, String> answer = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            answer.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(answer);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> answer = new HashMap<>();
        answer.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(answer);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> answer = new HashMap<>();
        answer.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(answer);
    }
}
