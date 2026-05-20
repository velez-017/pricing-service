package com.techplanner.pricingservice.exceptions;

public class PriceNotFoundException extends RuntimeException {

    private final Long id;

    public PriceNotFoundException(Long id) {
        super("Price with id " + id + " not found");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}