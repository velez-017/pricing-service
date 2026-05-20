package com.techplanner.pricingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class PriceRequestDto {

    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 2, max = 100,
            message = "Product name must contain between 2 and 100 characters")
    private String productName;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Region is required")
    private Long regionId;

    public PriceRequestDto() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
}