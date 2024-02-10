package com.example.demo.controller.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class PriceTimeDto {
    private LocalDateTime start;
    private LocalDateTime end;
}
