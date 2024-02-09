package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@RestController
public class PriceFinderController {
    @GetMapping("/prices")
    public ResponseEntity<PriceFindResponseDto> findPrice(@Valid @ModelAttribute PriceFindRequestDto request) {
        var time = request.getTime();

        if (10 == time.getHour() && 14 == time.getDayOfMonth()) {
            PriceDto price = new PriceDto()
                    .setAmount(new BigDecimal("35.50"))
                    .setCurrency("EUR")
                    .setId(1L)
                    .setTime(new PriceTimeDto()
                            .setStart(LocalDateTime.parse("2020-06-14T00:00:00"))
                            .setEnd(LocalDateTime.parse("2020-12-31T23:59:59")));
            var dto = new PriceFindResponseDto()
                    .setProductId(35455L)
                    .setBrandId(1)
                    .setPrice(price);
            return ResponseEntity.ok(dto);
        }
        if (16 == time.getHour() && 14 == time.getDayOfMonth()) {
            PriceDto price = new PriceDto()
                    .setAmount(new BigDecimal("25.45"))
                    .setCurrency("EUR")
                    .setId(2L)
                    .setTime(new PriceTimeDto()
                            .setStart(LocalDateTime.parse("2020-06-14T15:00:00"))
                            .setEnd(LocalDateTime.parse("2020-06-14T18:30:00")));
            var dto = new PriceFindResponseDto()
                    .setProductId(35455L)
                    .setBrandId(1)
                    .setPrice(price);
            return ResponseEntity.ok(dto);
        }
        if (21 == time.getHour() && 14 == time.getDayOfMonth()) {
            PriceDto price = new PriceDto()
                    .setAmount(new BigDecimal("35.50"))
                    .setCurrency("EUR")
                    .setId(1L)
                    .setTime(new PriceTimeDto()
                            .setStart(LocalDateTime.parse("2020-06-14T00:00:00"))
                            .setEnd(LocalDateTime.parse("2020-06-14T23:59:59")));
            var dto = new PriceFindResponseDto()
                    .setProductId(35455L)
                    .setBrandId(1)
                    .setPrice(price);
            return ResponseEntity.ok(dto);
        }
        if (10 == time.getHour() && 15 == time.getDayOfMonth()) {
            PriceDto price = new PriceDto()
                    .setAmount(new BigDecimal("30.50"))
                    .setCurrency("EUR")
                    .setId(3L)
                    .setTime(new PriceTimeDto()
                            .setStart(LocalDateTime.parse("2020-06-15T00:00:00"))
                            .setEnd(LocalDateTime.parse("2020-06-15T11:00:00")));
            var dto = new PriceFindResponseDto()
                    .setProductId(35455L)
                    .setBrandId(1)
                    .setPrice(price);
            return ResponseEntity.ok(dto);
        }

        if (21 == time.getHour() && 16 == time.getDayOfMonth()) {
            PriceDto price = new PriceDto()
                    .setAmount(new BigDecimal("35.50"))
                    .setCurrency("EUR")
                    .setId(1L)
                    .setTime(new PriceTimeDto()
                            .setStart(LocalDateTime.parse("2020-06-14T00:00:00"))
                            .setEnd(LocalDateTime.parse("2020-12-31T23:59:59")));
            var dto = new PriceFindResponseDto()
                    .setProductId(35455L)
                    .setBrandId(1)
                    .setPrice(price);
            return ResponseEntity.ok(dto);
        }

        PriceDto price = new PriceDto()
                .setAmount(new BigDecimal("35.50"))
                .setCurrency("EUR")
                .setId(1L)
                .setTime(new PriceTimeDto()
                        .setStart(LocalDateTime.parse("2020-06-14T00:00:00"))
                        .setEnd(LocalDateTime.parse("2020-12-31T23:59:59")));
        var dto = new PriceFindResponseDto()
                .setProductId(35455L)
                .setBrandId(1)
                .setPrice(price);
        return ResponseEntity.ok(dto);
    }
}
