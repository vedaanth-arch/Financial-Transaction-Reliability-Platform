package com.financialplatform.financial_transaction_reliability_platform.demo;

import java.time.Instant;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    public record ErrorBody(Instant timestamp, int status, String code, String message, Map<String,String> details) {}
    @ExceptionHandler(ApiException.class) ResponseEntity<ErrorBody> domain(ApiException e) { return response(e.status(), e.code(), e.getMessage(), Map.of()); }
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ErrorBody> validation(MethodArgumentNotValidException e) {
        Map<String,String> details = new LinkedHashMap<>(); e.getBindingResult().getFieldErrors().forEach(x -> details.putIfAbsent(x.getField(), x.getDefaultMessage()));
        return response(400, "VALIDATION_ERROR", "Request validation failed", details);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class) ResponseEntity<ErrorBody> malformed(HttpMessageNotReadableException e) { return response(400,"MALFORMED_REQUEST","Request JSON is invalid",Map.of()); }
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    ResponseEntity<ErrorBody> badParameter(Exception e) { return response(400,"INVALID_PARAMETER","A path or query parameter is missing or has an invalid format",Map.of()); }
    private ResponseEntity<ErrorBody> response(int status, String code, String message, Map<String,String> details) {
        return ResponseEntity.status(status).body(new ErrorBody(Instant.now(), status, code, message, details));
    }
}
