package com.example.demo.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DataJpaTest
@Import(PriceRepositoryAdapter.class)
class PriceRepositoryTests {
    @Autowired
    @SpyBean
    private PriceRepository repository;

    @Autowired
    private PriceRepositoryAdapter adapter;

    @Test
    public void shouldRetrievePrice_whenPriceIsPresent() {
        var priceEntity = new PriceEntity().setProductId(1L)
                .setBrandId(1)
                .setStart(LocalDateTime.parse("2020-06-14T00:00:00"))
                .setEnd(LocalDateTime.parse("2020-12-31T23:59:59"))
                .setId(1L)
                .setPriority(0)
                .setPrice(new BigDecimal("35.50"))
                .setCurrency("EUR");
        repository.save(priceEntity);


        var price = adapter.findPrice(1L, 1, LocalDateTime.parse("2020-06-14T10:00:00"));

        assertThat(price.get())
                .isEqualTo(priceEntity.toPrice())
                .usingRecursiveComparison();
        verify(repository, times(1))
                .findPrice(eq(1L), eq(1), eq(LocalDateTime.parse("2020-06-14T10:00:00")));
    }

    @Test
    public void shouldReturnEmpty_whenPriceIsNotPresent() {
        var price = adapter.findPrice(1L, 1, LocalDateTime.parse("2019-06-14T00:00:00"));
        assertThat(price).isEmpty();
    }
}