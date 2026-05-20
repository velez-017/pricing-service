package com.techplanner.pricingservice.repositories;

import com.techplanner.pricingservice.entities.Price;
import com.techplanner.pricingservice.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPriceDao extends JpaRepository<Price, Long> {

    @Query("from Region")
    List<Region> findAllRegions();
}