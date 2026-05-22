package com.techplanner.pricingservice.repositories;

import com.techplanner.pricingservice.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPriceDao extends JpaRepository<Price, Long> {

}