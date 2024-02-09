package com.example.demo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PriceFindResponseDto {
    private Long productId;
    private Integer brandId;
    private PriceDto price;
}
