package com.example.demo;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PriceFindRequestDto {
    @NotNull
    private LocalDateTime time;
    @NotNull
    private Long productId;
    @NotNull
    private Integer brandId;
}
