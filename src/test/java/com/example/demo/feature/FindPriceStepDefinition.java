package com.example.demo.feature;

import com.example.demo.controller.dto.PriceDto;
import com.example.demo.controller.dto.PriceFindResponseDto;
import com.example.demo.controller.dto.PriceTimeDto;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class FindPriceStepDefinition {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    static class SharedContext {
        private PriceFindResponseDto response;
        private LocalDateTime requestTime;
    }

    private final SharedContext sharedContext = new SharedContext();


    @When("a price request is executed with {string} {int} {int}")
    public void a_price_request_is_executed_with(String time, Integer productId, Integer brandId) {
        var uri = buildUri(time, productId, brandId);

        ResponseEntity<PriceFindResponseDto> response = restTemplate.getForEntity(uri, PriceFindResponseDto.class);

        sharedContext.response = response.getBody();
        sharedContext.requestTime = LocalDateTime.parse(time);
    }

    private URI buildUri(String time, Integer productId, Integer brandId) {
        return UriComponentsBuilder.fromUriString("prices")
                .scheme("http")
                .host("localhost")
                .port(port)
                .queryParam("time", time)
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .build()
                .toUri();
    }

    @Then("a price response is expected with {long} {int} {long} {string} {string} {string}")
    public void a_price_response_is_expected_with(
            Long productId,
            Integer brandId,
            Long priceId,
            String startTime,
            String endTime,
            String expectedPrice) {
        log.info("Request time: {}", sharedContext.requestTime);
        PriceFindResponseDto expectedResponse = buildExpectedResponse(
                productId, brandId, priceId, startTime, endTime, expectedPrice);

        assertThat(sharedContext.response)
                .isEqualTo(expectedResponse)
                .usingRecursiveComparison();
    }

    private static PriceFindResponseDto buildExpectedResponse(
            Long productId,
            Integer brandId,
            Long priceId,
            String startTime,
            String endTime,
            String expectedPrice) {
        var startDateTime = LocalDateTime.parse(startTime);
        var endDateTime = LocalDateTime.parse(endTime);
        var splitPrice = expectedPrice.split(" ");
        var priceAmount = new BigDecimal(splitPrice[0]);
        var priceCurrency = splitPrice[1];

        return new PriceFindResponseDto()
                .setBrandId(brandId)
                .setProductId(productId)
                .setPrice(new PriceDto()
                        .setId(priceId)
                        .setAmount(priceAmount)
                        .setCurrency(priceCurrency)
                        .setTime(new PriceTimeDto()
                                .setStart(startDateTime)
                                .setEnd(endDateTime)));
    }
}


