package com.techplanner.pricingservice.controllers;

import com.techplanner.pricingservice.dto.PriceRequestDto;
import com.techplanner.pricingservice.dto.PriceResponseDto;
import com.techplanner.pricingservice.entities.Price;
import com.techplanner.pricingservice.services.IPriceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pricing-service")
@CrossOrigin(origins = {"http://localhost:4200"})
@Tag(
        name = "Pricing API",
        description = "Operations for managing product pricing"
)
public class PriceRestController {

    @Autowired
    private IPriceService priceService;

    @Operation(summary = "Get all prices")
    @GetMapping("/prices")
    public ResponseEntity<List<PriceResponseDto>> listPrices() {

        List<PriceResponseDto> prices = priceService.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();

        return ResponseEntity.ok(prices);
    }

    @Operation(summary = "Get paginated prices")
    @GetMapping("/prices/page/{page}")
    public ResponseEntity<Page<PriceResponseDto>> listPricesPage(
            @PathVariable Integer page) {

        Page<PriceResponseDto> prices = priceService.findAll(
                        PageRequest.of(page, 5)
                )
                .map(this::convertToDto);

        return ResponseEntity.ok(prices);
    }

    @Operation(summary = "Get price by ID")
    @GetMapping("/prices/{id}")
    public ResponseEntity<PriceResponseDto> findPrice(
            @PathVariable Long id) {

        Price price = priceService.findById(id);

        return ResponseEntity.ok(
                convertToDto(price)
        );
    }

    @Operation(summary = "Create a new price")
    @PostMapping("/prices")
    public ResponseEntity<PriceResponseDto> createPrice(
            @Valid @RequestBody PriceRequestDto dto) {

        Price price = new Price();

        price.setProductName(dto.getProductName());
        price.setAmount(dto.getAmount());
        price.setCustomerType(dto.getCustomerType());

        Price newPrice = priceService.save(price);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertToDto(newPrice));
    }

    @Operation(summary = "Update an existing price")
    @PutMapping("/prices/{id}")
    public ResponseEntity<PriceResponseDto> updatePrice(
            @PathVariable Long id,
            @Valid @RequestBody PriceRequestDto dto) {

        Price price = new Price();

        price.setProductName(dto.getProductName());
        price.setAmount(dto.getAmount());
        price.setCustomerType(dto.getCustomerType());

        Price updatedPrice =
                priceService.update(id, price);

        return ResponseEntity.ok(
                convertToDto(updatedPrice)
        );
    }

    @Operation(summary = "Delete a price")
    @DeleteMapping("/prices/{id}")
    public ResponseEntity<Void> deletePrice(
            @PathVariable Long id) {

        priceService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private PriceResponseDto convertToDto(Price price) {

        PriceResponseDto dto = new PriceResponseDto();

        dto.setId(price.getId());

        dto.setProductName(
                price.getProductName()
        );

        dto.setBasePrice(
                price.getAmount()
        );

        dto.setCustomerType(
                price.getCustomerType()
        );

        dto.setDiscountPercentage(
                price.getDiscountPercentage()
        );

        dto.setTaxPercentage(
                price.getTaxPercentage()
        );

        dto.setFinalPrice(
                price.getFinalPrice()
        );

        dto.setCreatedAt(
                price.getCreatedAt()
        );

        return dto;
    }
}git add .