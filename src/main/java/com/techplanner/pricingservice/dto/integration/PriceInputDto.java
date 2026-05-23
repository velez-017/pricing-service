package com.techplanner.pricingservice.dto.integration;

import com.techplanner.pricingservice.enums.CustomerType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PriceInputDto {

    @NotBlank(message = "Product name cannot be empty")
    @Size(
            min = 2,
            max = 100,
            message = "Product name must contain between 2 and 100 characters"
    )
    private String productName;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Customer type is required")
    private CustomerType customerType;
}