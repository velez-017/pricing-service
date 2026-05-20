package com.techplanner.pricingservice.controllers;

import com.techplanner.pricingservice.dto.PriceRequestDto;
import com.techplanner.pricingservice.dto.PriceResponseDto;
import com.techplanner.pricingservice.dto.RegionDto;
import com.techplanner.pricingservice.entities.Price;
import com.techplanner.pricingservice.entities.Region;
import com.techplanner.pricingservice.services.IPriceService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pricing-service")
@CrossOrigin(origins = {"http://localhost:4200"})
public class PriceRestController {

    @Autowired
    private IPriceService priceService;

    @GetMapping("/prices")
    public ResponseEntity<List<PriceResponseDto>> listPrices() {

        List<PriceResponseDto> prices = priceService.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();

        return ResponseEntity.ok(prices);
    }

    @GetMapping("/prices/page/{page}")
    public ResponseEntity<Page<PriceResponseDto>> listPricesPage(
            @PathVariable Integer page) {

        Page<PriceResponseDto> prices = priceService.findAll(
                        PageRequest.of(page, 5)
                )
                .map(this::convertToDto);

        return ResponseEntity.ok(prices);
    }

    @GetMapping("/prices/{id}")
    public ResponseEntity<PriceResponseDto> findPrice(
            @PathVariable Long id) {

        Price price = priceService.findById(id);

        return ResponseEntity.ok(
                convertToDto(price)
        );
    }

    @PostMapping("/prices")
    public ResponseEntity<?> createPrice(
            @Valid @RequestBody PriceRequestDto dto) {

        Optional<Region> optionalRegion =
                priceService.findRegionById(dto.getRegionId());

        if (optionalRegion.isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "message",
                            "Region not found"
                    ));
        }

        Price price = new Price();

        price.setProductName(dto.getProductName());
        price.setAmount(dto.getAmount());
        price.setRegion(optionalRegion.get());

        Price newPrice = priceService.save(price);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertToDto(newPrice));
    }

    @PutMapping("/prices/{id}")
    public ResponseEntity<PriceResponseDto> updatePrice(
            @PathVariable Long id,
            @Valid @RequestBody PriceRequestDto dto) {

        Optional<Region> optionalRegion =
                priceService.findRegionById(dto.getRegionId());

        if (optionalRegion.isEmpty()) {
            throw new RuntimeException("Region not found");
        }

        Price price = new Price();

        price.setProductName(dto.getProductName());
        price.setAmount(dto.getAmount());
        price.setRegion(optionalRegion.get());

        Price updatedPrice =
                priceService.update(id, price);

        return ResponseEntity.ok(
                convertToDto(updatedPrice)
        );
    }

    @DeleteMapping("/prices/{id}")
    public ResponseEntity<Void> deletePrice(
            @PathVariable Long id) {

        priceService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/prices/regions")
    public ResponseEntity<List<RegionDto>> listRegions() {

        List<RegionDto> regions = priceService.findAllRegions()
                .stream()
                .map(region -> new RegionDto(
                        region.getId(),
                        region.getName()
                ))
                .toList();

        return ResponseEntity.ok(regions);
    }

    private PriceResponseDto convertToDto(Price price) {

        return new PriceResponseDto(
                price.getId(),
                price.getProductName(),
                price.getAmount(),
                price.getCreatedAt(),
                new RegionDto(
                        price.getRegion().getId(),
                        price.getRegion().getName()
                )
        );
    }
}