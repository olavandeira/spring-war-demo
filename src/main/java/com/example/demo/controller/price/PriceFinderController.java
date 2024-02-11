package com.example.demo.controller.price;

import com.example.demo.controller.price.dto.PriceFindRequestDto;
import com.example.demo.controller.price.dto.PriceFindResponseDto;
import com.example.demo.controller.exception.NotFoundException;
import com.example.demo.service.PriceFinder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class PriceFinderController {

    private final PriceFinder priceFinder;

    @GetMapping("/prices")
    public ResponseEntity<PriceFindResponseDto> findPrice(@Valid @ModelAttribute PriceFindRequestDto params) {
        return priceFinder.findPrice(params.getProductId(), params.getBrandId(), params.getTime())
                .map(price -> ResponseEntity.ok(PriceFindResponseDto.of(price)))
                .orElseThrow(() -> new NotFoundException("Price not found", params));
    }
}
