package com.techplanner.pricingservice.controllers;

import com.techplanner.pricingservice.entities.Region;
import com.techplanner.pricingservice.repositories.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegionController {

    private final RegionRepository regionRepository;

    @GetMapping("/regions")
    public List<Region> getRegions() {
        return regionRepository.findAll();
    }
}