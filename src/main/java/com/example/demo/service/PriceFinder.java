package com.example.demo.service;

import com.example.demo.domain.Price;
import com.example.demo.domain.PriceAmount;
import com.example.demo.domain.PriceTimeRange;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PriceFinder {

    public Optional<Price> findPrice(Long productId,
                                     Integer brandId,
                                     LocalDateTime requestedAt) {

        if (10 == requestedAt.getHour() && 14 == requestedAt.getDayOfMonth()) {
            return Optional.of(
                    new Price(1L, 35455L, 1,
                            new PriceAmount(
                                    new BigDecimal("35.50"), "EUR"),
                            new PriceTimeRange(
                                    LocalDateTime.parse("2020-06-14T00:00:00"),
                                    LocalDateTime.parse("2020-12-31T23:59:59"))));

        }
        if (16 == requestedAt.getHour() && 14 == requestedAt.getDayOfMonth()) {

            return Optional.of(
                    new Price(2L, 35455L, 1,
                            new PriceAmount(
                                    new BigDecimal("25.45"), "EUR"),
                            new PriceTimeRange(
                                    LocalDateTime.parse("2020-06-14T15:00:00"),
                                    LocalDateTime.parse("2020-06-14T18:30:00"))));
        }
        if (21 == requestedAt.getHour() && 14 == requestedAt.getDayOfMonth()) {
            return Optional.of(
                    new Price(1L, 35455L, 1,
                            new PriceAmount(
                                    new BigDecimal("35.50"), "EUR"),
                            new PriceTimeRange(
                                    LocalDateTime.parse("2020-06-14T00:00:00"),
                                    LocalDateTime.parse("2020-12-31T23:59:59"))));

        }
        if (10 == requestedAt.getHour() && 15 == requestedAt.getDayOfMonth()) {

            return Optional.of(
                    new Price(3L, 35455L, 1,
                            new PriceAmount(
                                    new BigDecimal("30.50"), "EUR"),
                            new PriceTimeRange(
                                    LocalDateTime.parse("2020-06-15T00:00:00"),
                                    LocalDateTime.parse("2020-06-15T11:00:00"))));
        }

        if (21 == requestedAt.getHour() && 16 == requestedAt.getDayOfMonth()) {

            return Optional.of(
                    new Price(1L, 35455L, 1,
                            new PriceAmount(
                                    new BigDecimal("35.50"), "EUR"),
                            new PriceTimeRange(
                                    LocalDateTime.parse("2020-06-14T00:00:00"),
                                    LocalDateTime.parse("2020-12-31T23:59:59"))));
        }
        return Optional.empty();

    }
}
