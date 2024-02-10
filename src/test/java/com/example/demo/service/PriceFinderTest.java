package com.example.demo.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceFinderTest {

    private final PriceFinder priceFinder = new PriceFinder();

    @Test
    public void shouldReturnPrice(){
        var price = priceFinder.findPrice(1L, 1, LocalDateTime.parse("2020-06-14T10:00:00"));
        assertThat(price).isPresent();
    }


    @Test
    public void shouldReturnEmpty(){
        var price = priceFinder.findPrice(1L, 1, LocalDateTime.parse("2019-06-14T00:00:00"));
        assertThat(price).isEmpty();
    }
}