package com.techplanner.pricingservice.services;

import com.techplanner.pricingservice.entities.Price;
import com.techplanner.pricingservice.entities.Region;
import com.techplanner.pricingservice.exceptions.PriceNotFoundException;
import com.techplanner.pricingservice.repositories.IPriceDao;
import com.techplanner.pricingservice.repositories.RegionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PriceServiceImpl implements IPriceService {

    @Autowired
    private IPriceDao priceDao;

    @Autowired
    private RegionRepository regionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Price> findAll() {
        return priceDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Price> findAll(Pageable pageable) {
        return priceDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Price findById(Long id) {

        return priceDao.findById(id)
                .orElseThrow(() ->
                        new PriceNotFoundException(id));
    }

    @Override
    public Price save(Price price) {
        return priceDao.save(price);
    }

    @Override
    public Price update(Long id, Price price) {

        Price currentPrice = priceDao.findById(id)
                .orElseThrow(() ->
                        new PriceNotFoundException(id));

        currentPrice.setProductName(price.getProductName());
        currentPrice.setAmount(price.getAmount());
        currentPrice.setRegion(price.getRegion());

        return priceDao.save(currentPrice);
    }

    @Override
    public void delete(Long id) {

        if (!priceDao.existsById(id)) {
            throw new PriceNotFoundException(id);
        }

        priceDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Region> findRegionById(Long id) {
        return regionRepository.findById(id);
    }
}