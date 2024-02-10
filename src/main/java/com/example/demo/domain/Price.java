package com.example.demo.domain;

public record Price(Long id, Long productId, Integer brandId, PriceAmount amount, PriceTimeRange timeRange) {
}
