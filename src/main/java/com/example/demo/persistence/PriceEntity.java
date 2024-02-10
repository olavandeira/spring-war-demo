package com.example.demo.persistence;

import com.example.demo.domain.Price;
import com.example.demo.domain.PriceAmount;
import com.example.demo.domain.PriceTimeRange;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "price")
@NoArgsConstructor
@Accessors(chain = true)
public class PriceEntity {
    @Id
    private Long id;
    private Long productId;
    private Integer brandId;
    @Column(name = "date_start")
    private LocalDateTime start;
    @Column(name = "date_end")
    private LocalDateTime end;
    private Integer priority;
    private BigDecimal price;
    @Column(name = "price_currency")
    private String currency;

    public Price toPrice() {
        return new Price(id, productId, brandId, new PriceAmount(price, currency), new PriceTimeRange(start, end));
    }
}
