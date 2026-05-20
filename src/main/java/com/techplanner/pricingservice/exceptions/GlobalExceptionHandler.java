package com.techplanner.pricingservice.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // =====================================================
    // VALIDATION ERRORS - @Valid RequestBody
    // =====================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse>
    handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {

                    String fieldName =
                            ((FieldError) error).getField();

                    String errorMessage =
                            error.getDefaultMessage();

                    errors.put(fieldName, errorMessage);
                });

        log.debug("Validation errors: {}", errors);

        ValidationErrorResponse response =
                new ValidationErrorResponse();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Validation error");
        response.setErrors(errors);

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    // =====================================================
    // VALIDATION ERRORS - Request Params / Path Variables
    // =====================================================

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse>
    handleConstraintViolation(
            ConstraintViolationException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation :
                ex.getConstraintViolations()) {

            errors.put(
                    violation.getPropertyPath().toString(),
                    violation.getMessage()
            );
        }

        log.debug("Constraint violations: {}", errors);

        ValidationErrorResponse response =
                new ValidationErrorResponse();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Validation error");
        response.setErrors(errors);

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    // =====================================================
    // 404 NOT FOUND
    // =====================================================

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handlePriceNotFoundException(
            PriceNotFoundException ex
    ) {

        log.warn("Price not found: id={}", ex.getId());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    // =====================================================
    // 500 INTERNAL SERVER ERROR
    // =====================================================

    @ExceptionHandler(PriceServiceException.class)
    public ResponseEntity<ErrorResponse>
    handlePriceServiceException(
            PriceServiceException ex
    ) {

        log.error("Service error", ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error"
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    // =====================================================
    // GENERIC ERROR
    // =====================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGeneralException(
            Exception ex
    ) {

        log.error("Unhandled exception", ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error"
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}