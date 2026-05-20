package com.techplanner.pricingservice.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should handle constraint violation exception")
    void shouldHandleConstraintViolationException() {

        ConstraintViolation<?> violation =
                mock(ConstraintViolation.class);

        Path path = mock(Path.class);

        when(path.toString())
                .thenReturn("regionId");

        when(violation.getPropertyPath())
                .thenReturn(path);

        when(violation.getMessage())
                .thenReturn("Region is required");

        Set<ConstraintViolation<?>> violations =
                Set.of(violation);

        ConstraintViolationException ex =
                new ConstraintViolationException(violations);

        ResponseEntity<ValidationErrorResponse> response =
                handler.handleConstraintViolation(ex);

        assertEquals(400, response.getStatusCode().value());

        assertEquals(
                "Validation error",
                response.getBody().getMessage()
        );

        assertEquals(
                "Region is required",
                response.getBody()
                        .getErrors()
                        .get("regionId")
        );
    }
}