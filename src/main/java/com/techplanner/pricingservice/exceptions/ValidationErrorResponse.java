package com.techplanner.pricingservice.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ValidationErrorResponse {

    private int status;
    private String message;
    private Map<String, String> errors;
}