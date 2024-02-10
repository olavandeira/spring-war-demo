package com.example.demo.domain;


import java.math.BigDecimal;


public record PriceAmount(BigDecimal amount, String currency) {
}
