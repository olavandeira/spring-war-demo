package com.example.demo.service;

import com.example.demo.domain.Price;
import com.example.demo.persistence.PriceRepositoryAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PriceFinder {
    private final PriceRepositoryAdapter repo;
    public Optional<Price> findPrice(Long productId,
                                     Integer brandId,
                                     LocalDateTime requestedAt) {

        return repo.findPrice(productId, brandId, requestedAt);
    }
}
