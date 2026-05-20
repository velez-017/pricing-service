package com.techplanner.pricingservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceResponseDto {

    private Long id;
    private String productName;
    private BigDecimal amount;
    private LocalDate createdAt;
    private RegionDto region;

    public PriceResponseDto() {
    }

    public PriceResponseDto(
            Long id,
            String productName,
            BigDecimal amount,
            LocalDate createdAt,
            RegionDto region
    ) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
        this.createdAt = createdAt;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public RegionDto getRegion() {
        return region;
    }

    public void setRegion(RegionDto region) {
        this.region = region;
    }
}