package com.gerenciador.producao.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCustomException(Exception e) {

        var response = new HashMap<>();

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleCustomException(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        Map<String, String> erros = new HashMap<>();
        for (ConstraintViolation<?> violation: violations) {
            String fieldName = violation.getPropertyPath().toString();
            String messageTemplate = violation.getMessageTemplate();

            erros.put(fieldName, messageTemplate);
        }
        var response = new HashMap<>();

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", erros);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
