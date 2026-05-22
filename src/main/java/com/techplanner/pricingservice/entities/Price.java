package com.techplanner.pricingservice.entities;

import com.techplanner.pricingservice.enums.CustomerType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "prices")
@Getter
@Setter
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    @Size(
            min = 2,
            max = 100,
            message = "Product name must contain between 2 and 100 characters"
    )
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "tax_percentage", precision = 5, scale = 2)
    private BigDecimal taxPercentage;

    @Column(name = "final_price", precision = 10, scale = 2)
    private BigDecimal finalPrice;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @PrePersist
    public void prePersist() {

        this.createdAt = LocalDate.now();

        calculatePricing();
    }

    @PreUpdate
    public void preUpdate() {

        calculatePricing();
    }

    private void calculatePricing() {

        BigDecimal discount = switch (customerType) {

            case EXECUTIVE -> new BigDecimal("15");
            case ADMINISTRATIVE -> new BigDecimal("10");
            default -> new BigDecimal("5");
        };

        this.discountPercentage = discount;

        this.taxPercentage = new BigDecimal("19");

        BigDecimal discountAmount = amount
                .multiply(discount)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        BigDecimal subtotal = amount.subtract(discountAmount);

        BigDecimal taxAmount = subtotal
                .multiply(taxPercentage)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        this.finalPrice = subtotal.add(taxAmount);
    }
}