package com.example.demo.controller.dto;

import com.example.demo.domain.Price;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PriceFindResponseDto {
    private Long productId;
    private Integer brandId;
    private PriceDto price;

    public static PriceFindResponseDto of(Price price) {
        return new PriceFindResponseDto()
                .setProductId(price.productId())
                .setBrandId(price.brandId())
                .setPrice(new PriceDto()
                        .setId(price.id())
                        .setAmount(price.amount().amount())
                        .setCurrency(price.amount().currency())
                        .setTime(new PriceTimeDto()
                                .setStart(price.timeRange().start())
                                .setEnd(price.timeRange().end())
                        )
                );
    }
}
