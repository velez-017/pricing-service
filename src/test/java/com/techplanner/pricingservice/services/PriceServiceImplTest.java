package com.techplanner.pricingservice.services;

import com.techplanner.pricingservice.entities.Price;
import com.techplanner.pricingservice.entities.Region;
import com.techplanner.pricingservice.exceptions.PriceNotFoundException;
import com.techplanner.pricingservice.repositories.IPriceDao;
import com.techplanner.pricingservice.repositories.RegionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests - PriceServiceImpl")
class PriceServiceImplTest {

    @Mock
    private IPriceDao priceDao;

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    private Price price;
    private Region region;

    @BeforeEach
    void setUp() {

        region = new Region();
        region.setId(1L);
        region.setName("North America");

        price = new Price();
        price.setId(1L);
        price.setProductName("MacBook Pro");
        price.setAmount(new BigDecimal("2999.99"));
        price.setCreatedAt(LocalDate.now());
        price.setRegion(region);
    }

    // =====================================================
    // FIND ALL
    // =====================================================

    @Test
    @DisplayName("findAll - should return all prices")
    void findAll_shouldReturnAllPrices() {

        when(priceDao.findAll())
                .thenReturn(List.of(price));

        List<Price> result = priceService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProductName())
                .isEqualTo("MacBook Pro");

        verify(priceDao, times(1))
                .findAll();
    }

    // =====================================================
    // FIND BY ID
    // =====================================================

    @Test
    @DisplayName("findById - existing ID should return price")
    void findById_existingId_shouldReturnPrice() {

        when(priceDao.findById(1L))
                .thenReturn(Optional.of(price));

        Price result = priceService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProductName())
                .isEqualTo("MacBook Pro");

        verify(priceDao, times(1))
                .findById(1L);
    }

    @Test
    @DisplayName("findById - non existing ID should throw exception")
    void findById_nonExistingId_shouldThrowException() {

        when(priceDao.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                priceService.findById(999L))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("999");

        verify(priceDao, times(1))
                .findById(999L);
    }

    // =====================================================
    // SAVE
    // =====================================================

    @Test
    @DisplayName("save - should save and return price")
    void save_shouldSaveAndReturnPrice() {

        when(priceDao.save(price))
                .thenReturn(price);

        Price result = priceService.save(price);

        assertThat(result).isNotNull();
        assertThat(result.getProductName())
                .isEqualTo("MacBook Pro");

        verify(priceDao, times(1))
                .save(price);
    }

    @Test
    @DisplayName("save - database error should throw exception")
    void save_databaseError_shouldThrowException() {

        when(priceDao.save(price))
                .thenThrow(
                        new DataIntegrityViolationException(
                                "Database error"
                        )
                );

        assertThatThrownBy(() ->
                priceService.save(price))
                .isInstanceOf(DataIntegrityViolationException.class);

        verify(priceDao, times(1))
                .save(price);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @Test
    @DisplayName("update - existing ID should update and return price")
    void update_existingId_shouldUpdateAndReturnPrice() {

        Price updatedData = new Price();
        updatedData.setProductName("MacBook Air");
        updatedData.setAmount(new BigDecimal("1999.99"));
        updatedData.setRegion(region);

        when(priceDao.findById(1L))
                .thenReturn(Optional.of(price));

        when(priceDao.save(any(Price.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        Price result =
                priceService.update(1L, updatedData);

        assertThat(result.getProductName())
                .isEqualTo("MacBook Air");

        assertThat(result.getAmount())
                .isEqualByComparingTo("1999.99");

        verify(priceDao, times(1))
                .findById(1L);

        verify(priceDao, times(1))
                .save(any(Price.class));
    }

    @Test
    @DisplayName("update - non existing ID should throw exception")
    void update_nonExistingId_shouldThrowException() {

        when(priceDao.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                priceService.update(999L, price))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("999");

        verify(priceDao, times(1))
                .findById(999L);

        verify(priceDao, never())
                .save(any());
    }

    // =====================================================
    // DELETE
    // =====================================================

    @Test
    @DisplayName("delete - existing ID should delete price")
    void delete_existingId_shouldDeletePrice() {

        when(priceDao.existsById(1L))
                .thenReturn(true);

        doNothing().when(priceDao)
                .deleteById(1L);

        priceService.delete(1L);

        verify(priceDao, times(1))
                .existsById(1L);

        verify(priceDao, times(1))
                .deleteById(1L);
    }

    @Test
    @DisplayName("delete - non existing ID should throw exception")
    void delete_nonExistingId_shouldThrowException() {

        when(priceDao.existsById(999L))
                .thenReturn(false);

        assertThatThrownBy(() ->
                priceService.delete(999L))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("999");

        verify(priceDao, times(1))
                .existsById(999L);

        verify(priceDao, never())
                .deleteById(any());
    }

    // =====================================================
    // REGIONS
    // =====================================================

    @Test
    @DisplayName("findAllRegions - should return regions")
    void findAllRegions_shouldReturnRegions() {

        when(regionRepository.findAll())
                .thenReturn(List.of(region));

        List<Region> result =
                priceService.findAllRegions();

        assertThat(result).hasSize(1);

        assertThat(result.get(0).getName())
                .isEqualTo("North America");

        verify(regionRepository, times(1))
                .findAll();
    }

    @Test
    @DisplayName("findRegionById - existing ID should return region")
    void findRegionById_existingId_shouldReturnRegion() {

        when(regionRepository.findById(1L))
                .thenReturn(Optional.of(region));

        Optional<Region> result =
                priceService.findRegionById(1L);

        assertThat(result).isPresent();

        assertThat(result.get().getName())
                .isEqualTo("North America");

        verify(regionRepository, times(1))
                .findById(1L);
    }

    @Test
    @DisplayName("findRegionById - non existing ID should return empty")
    void findRegionById_nonExistingId_shouldReturnEmpty() {

        when(regionRepository.findById(999L))
                .thenReturn(Optional.empty());

        Optional<Region> result =
                priceService.findRegionById(999L);

        assertThat(result).isEmpty();

        verify(regionRepository, times(1))
                .findById(999L);
    }
}