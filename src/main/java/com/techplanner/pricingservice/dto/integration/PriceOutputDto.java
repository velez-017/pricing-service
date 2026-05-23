package com.techplanner.pricingservice.dto.integration;

import com.techplanner.pricingservice.enums.CustomerType;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PriceOutputDto {

    private Long id;

    private String productName;

    private BigDecimal basePrice;

    private CustomerType customerType;

    private BigDecimal discountPercentage;

    private BigDecimal taxPercentage;

    private BigDecimal finalPrice;

    private LocalDate createdAt;
}