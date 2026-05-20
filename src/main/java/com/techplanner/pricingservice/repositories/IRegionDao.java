package com.techplanner.pricingservice.repositories;

import com.techplanner.pricingservice.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRegionDao extends JpaRepository<Region, Long> {
}