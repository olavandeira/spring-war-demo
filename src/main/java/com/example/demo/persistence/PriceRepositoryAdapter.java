package com.example.demo.persistence;

import com.example.demo.domain.Price;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PriceRepositoryAdapter {

    private final PriceRepository repository;

    public Optional<Price> findPrice(Long productId, Integer brandId, LocalDateTime requestedAt) {
        return repository.findPrice(productId, brandId, requestedAt)
                .map(PriceEntity::toPrice);
    }
}
