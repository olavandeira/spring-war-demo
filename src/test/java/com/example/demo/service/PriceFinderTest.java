package com.example.demo.service;

import com.example.demo.domain.Price;
import com.example.demo.domain.PriceAmount;
import com.example.demo.domain.PriceTimeRange;
import com.example.demo.persistence.PriceRepositoryAdapter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

class PriceFinderTest {
    private final PriceRepositoryAdapter repo = mock(PriceRepositoryAdapter.class);
    private final PriceFinder priceFinder = new PriceFinder(repo);

    @Test
    public void shouldReturnPrice(){
        var expectedPrice = new Price(1L, 35455L, 1,
                new PriceAmount(
                        new BigDecimal("35.50"), "EUR"),
                new PriceTimeRange(
                        LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59")));
        given(repo.findPrice(any(), any(), any()))
                .willReturn(Optional.of(expectedPrice));
        var productId = 1L;
        var brandId = 1;
        var requestedAt = LocalDateTime.parse("2020-06-14T10:00:00");

        var price = priceFinder.findPrice(productId, brandId, requestedAt);

        assertThat(price.get())
                .isEqualTo(expectedPrice)
                .usingRecursiveComparison();
        verify(repo, times(1))
                .findPrice(eq(productId), eq(brandId), eq(requestedAt));
    }


    @Test
    public void shouldReturnEmpty(){
        var price = priceFinder.findPrice(1L, 1, LocalDateTime.parse("2019-06-14T00:00:00"));
        assertThat(price).isEmpty();
    }
}