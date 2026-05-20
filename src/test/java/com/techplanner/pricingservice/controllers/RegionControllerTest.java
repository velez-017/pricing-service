package com.techplanner.pricingservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techplanner.pricingservice.entities.Region;
import com.techplanner.pricingservice.repositories.RegionRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegionController.class)
@DisplayName("Controller tests - RegionController")
class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegionRepository regionRepository;

    @Test
    @DisplayName("GET /regions should return regions")
    void getRegions_shouldReturnRegions() throws Exception {

        Region region = new Region();
        region.setId(1L);
        region.setName("North America");

        when(regionRepository.findAll())
                .thenReturn(List.of(region));

        mockMvc.perform(get("/regions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("North America"));
    }
}