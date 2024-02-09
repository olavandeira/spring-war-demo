package com.example.demo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class PriceDto {
    private Long id;
    private BigDecimal amount;
    private String currency;
    private PriceTimeDto time;
}
