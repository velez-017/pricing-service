package com.techplanner.pricingservice.services;

import com.techplanner.pricingservice.entities.Price;
import com.techplanner.pricingservice.entities.Region;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPriceService {

    List<Price> findAll();

    Page<Price> findAll(Pageable pageable);

    Price findById(Long id);

    Price save(Price price);

    Price update(Long id, Price price);

    void delete(Long id);

    List<Region> findAllRegions();

    Optional<Region> findRegionById(Long id);
}