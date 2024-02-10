package com.example.demo.controller;

import com.example.demo.configuration.ObjectMapperConfiguration;
import com.example.demo.controller.error.ApplicationControllerAdvice;
import com.example.demo.controller.price.PriceFinderController;
import com.example.demo.domain.Price;
import com.example.demo.domain.PriceAmount;
import com.example.demo.domain.PriceTimeRange;
import com.example.demo.service.PriceFinder;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PriceFinderController.class)
public class PriceFinderControllerTest {

    @Autowired
    private PriceFinderController priceFinderController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceFinder priceFinder;

    private static void assertNullConstraintValidation(MvcResult result) throws UnsupportedEncodingException {
        var contentAsString = result.getResponse().getContentAsString();

        assertThatJson(contentAsString).node("type").isEqualTo("validation");

        JsonParser.parseString(contentAsString)
                .getAsJsonObject().get("errors")
                .getAsJsonArray()
                .forEach(x -> {
                    assertThatJson(x.toString()).node("code")
                            .isEqualTo("NotNull");
                    assertThatJson(x.toString()).node("message")
                            .isEqualTo(x.getAsJsonObject().get("field").getAsString() + " must not be null");
                });
    }

    private static void assertModelDefinition(MvcResult result, Price serviceResponse) throws UnsupportedEncodingException {
        var stringResponse = result.getResponse().getContentAsString();
        assertThatJson(stringResponse).node("productId")
                .isIntegralNumber()
                .isEqualTo(BigInteger.valueOf(serviceResponse.productId()));
        assertThatJson(stringResponse).node("brandId")
                .isIntegralNumber()
                .isEqualTo(BigInteger.valueOf(serviceResponse.brandId()));
        var price = assertThatJson(stringResponse).node("price");
        price.isObject();
        price.node("id")
                .isIntegralNumber()
                .isEqualTo(BigInteger.valueOf(serviceResponse.id()));
        price.node("amount")
                .isNumber()
                .isEqualTo(serviceResponse.amount().amount());
        price.node("currency")
                .isString()
                .isEqualTo(serviceResponse.amount().currency());

        price.node("time").node("end")
                .isString()
                .isEqualTo(serviceResponse.timeRange().end().format(DateTimeFormatter.ISO_DATE_TIME));

        price.node("time").node("start")
                .isString()
                .isEqualTo(serviceResponse.timeRange().start().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    private static void assertPriceNotFoundResponse(MvcResult result) throws UnsupportedEncodingException {
        var contentAsString = result.getResponse().getContentAsString();

        assertThatJson(contentAsString).node("type")
                .isEqualTo("not_found");
        assertThatJson(contentAsString).node("errors")
                .isArray()
                .hasSize(1);
        var errors = assertThatJson(contentAsString).node("errors").node("[0]");
        errors.node("message")
                .isEqualTo("Price not found");
        errors.node("requestParams")
                .isObject();
        errors.node("requestParams").node("time")
                .isEqualTo("2021-01-01T00:00:00");
        errors.node("requestParams").node("productId")
                .isEqualTo(35455);
        errors.node("requestParams").node("brandId")
                .isEqualTo(1);
    }

    @BeforeEach
    public void setup() {
        var configuredMapper = new ObjectMapperConfiguration().objectMapper();
        var applicationControllerAdvice = new ApplicationControllerAdvice(configuredMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(priceFinderController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(configuredMapper))
                .setControllerAdvice(applicationControllerAdvice)
                .build();
    }

    @Test
    void shouldRetrieveBadRequest_whenRequestsTriggersFieldValidation() throws Exception {
        mockMvc.perform(get("/prices"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRetrievePrice() throws Exception {
        Price serviceResponse =
                new Price(1L, 35455L, 1,
                        new PriceAmount(
                                new BigDecimal("35.50"), "EUR"),
                        new PriceTimeRange(
                                LocalDateTime.parse("2020-06-14T00:00:00"),
                                LocalDateTime.parse("2020-12-31T23:59:59")));

        given(priceFinder.findPrice(any(), any(), any()))
                .willReturn(Optional.of(serviceResponse));

        mockMvc.perform(
                        get("/prices")
                                .param("time", "2021-01-01T00:00:00")
                                .param("productId", "35455")
                                .param("brandId", "1")
                )
                .andExpect(status().isOk())
                .andDo(result -> assertModelDefinition(result, serviceResponse))
                .andDo(result -> verify(priceFinder, times(1))
                        .findPrice(eq(35455L), eq(1), eq(LocalDateTime.parse("2021-01-01T00:00:00"))));

    }

    @Test
    void shouldRetrieveFieldValidationError_whenAFieldIsEmpty() throws Exception {
        mockMvc.perform(get("/prices"))
                .andDo(PriceFinderControllerTest::assertNullConstraintValidation);
    }

    @Test
    void shouldRetrieveNotFound_whenPriceIsNotFound() throws Exception {
        mockMvc.perform(
                        get("/prices")
                                .param("time", "2021-01-01T00:00:00")
                                .param("productId", "35455")
                                .param("brandId", "1")
                )
                .andExpect(status().isNotFound())
                .andDo(PriceFinderControllerTest::assertPriceNotFoundResponse);
    }
}
