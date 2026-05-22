package com.techplanner.pricingservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techplanner.pricingservice.dto.PriceRequestDto;
import com.techplanner.pricingservice.entities.Price;
import com.techplanner.pricingservice.exceptions.PriceNotFoundException;
import com.techplanner.pricingservice.services.IPriceService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceRestController.class)
@DisplayName("Controller tests - PriceRestController")
class PriceRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IPriceService priceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /prices should return all prices")
    void listPrices_shouldReturnPrices() throws Exception {

        Price price = new Price();
        price.setId(1L);
        price.setProductName("MacBook Pro");
        price.setAmount(new BigDecimal("2999.99"));
        price.setCreatedAt(LocalDate.now());

        when(priceService.findAll())
                .thenReturn(List.of(price));

        mockMvc.perform(
                        get("/api/v1/pricing-service/prices")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName")
                        .value("MacBook Pro"))

                .andExpect(jsonPath("$[0].amount")
                        .value(2999.99));
    }

    @Test
    @DisplayName("GET /prices/{id} existing id")
    void findPrice_existingId_shouldReturnPrice() throws Exception {

        Price price = new Price();
        price.setId(1L);
        price.setProductName("MacBook Pro");
        price.setAmount(new BigDecimal("2999.99"));
        price.setCreatedAt(LocalDate.now());

        when(priceService.findById(1L))
                .thenReturn(price);

        mockMvc.perform(
                        get("/api/v1/pricing-service/prices/1")
                )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.productName")
                        .value("MacBook Pro"))

                .andExpect(jsonPath("$.amount")
                        .value(2999.99));
    }

    @Test
    @DisplayName("GET /prices/{id} non existing id")
    void findPrice_nonExistingId_shouldReturn404() throws Exception {

        when(priceService.findById(999L))
                .thenThrow(new PriceNotFoundException(999L));

        mockMvc.perform(
                        get("/api/v1/pricing-service/prices/999")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /prices should create price")
    void createPrice_shouldReturnCreated() throws Exception {

        Price savedPrice = new Price();
        savedPrice.setId(1L);
        savedPrice.setProductName("MacBook Pro");
        savedPrice.setAmount(new BigDecimal("2999.99"));
        savedPrice.setCreatedAt(LocalDate.now());

        PriceRequestDto dto = new PriceRequestDto();
        dto.setProductName("MacBook Pro");
        dto.setAmount(new BigDecimal("2999.99"));

        when(priceService.save(any(Price.class)))
                .thenReturn(savedPrice);

        mockMvc.perform(
                        post("/api/v1/pricing-service/prices")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.productName")
                        .value("MacBook Pro"))

                .andExpect(jsonPath("$.amount")
                        .value(2999.99));
    }

    @Test
    @DisplayName("POST /prices invalid request should return 400")
    void createPrice_invalidRequest_shouldReturn400() throws Exception {

        PriceRequestDto dto = new PriceRequestDto();

        dto.setProductName("");
        dto.setAmount(new BigDecimal("-10"));

        mockMvc.perform(
                        post("/api/v1/pricing-service/prices")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /prices/{id} should update price")
    void updatePrice_shouldReturnUpdatedPrice() throws Exception {

        Price updatedPrice = new Price();
        updatedPrice.setId(1L);
        updatedPrice.setProductName("MacBook Air");
        updatedPrice.setAmount(new BigDecimal("1999.99"));
        updatedPrice.setCreatedAt(LocalDate.now());

        PriceRequestDto dto = new PriceRequestDto();
        dto.setProductName("MacBook Air");
        dto.setAmount(new BigDecimal("1999.99"));

        when(priceService.update(any(Long.class), any(Price.class)))
                .thenReturn(updatedPrice);

        mockMvc.perform(
                        put("/api/v1/pricing-service/prices/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.productName")
                        .value("MacBook Air"))

                .andExpect(jsonPath("$.amount")
                        .value(1999.99));
    }

    @Test
    @DisplayName("DELETE /prices/{id} should return 204")
    void deletePrice_shouldReturnNoContent() throws Exception {

        mockMvc.perform(
                        delete("/api/v1/pricing-service/prices/1")
                )
                .andExpect(status().isNoContent());
    }
}