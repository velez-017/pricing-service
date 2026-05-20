package com.techplanner.pricingservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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

    @Column(name = "created_at")
    private LocalDate createdAt;

    @NotNull(message = "Region is required")

    // CAMBIO IMPORTANTE:
    // Se cambia LAZY por EAGER para evitar LazyInitializationException
    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "region_id", nullable = false)

    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler"
    })
    private Region region;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }
}

