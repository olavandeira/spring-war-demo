package com.example.demo.controller;

import com.example.demo.PriceFinderController;
import com.example.demo.RestControllerAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.format.DateTimeFormatter;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PriceFinderController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PriceFinderControllerTest {

    @Autowired
    private PriceFinderController priceFinderController;

    @Autowired
    private MockMvc mockMvc;

    private static void assertNullConstraintValidation(String contentAsString) {
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

    private static void assertModelDefinition(String stringResponse) {
        assertThatJson(stringResponse).node("productId").isIntegralNumber();
        assertThatJson(stringResponse).node("brandId").isIntegralNumber();

        assertThatJson(stringResponse).node("price").isObject();
        assertThatJson(stringResponse).node("price").node("id").isIntegralNumber();
        assertThatJson(stringResponse).node("price").node("amount").isNumber();
        assertThatJson(stringResponse).node("price").node("currency").isString();

        assertThatJson(stringResponse).node("price").node("time").node("end").isString()
                .matches(x -> {
                    DateTimeFormatter.ISO_DATE_TIME.parse(x);
                    return true;
                });

        assertThatJson(stringResponse).node("price").node("time").node("start").isString()
                .matches(x -> {
                    DateTimeFormatter.ISO_DATE_TIME.parse(x);
                    return true;
                });

    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(priceFinderController)
                .setMessageConverters(configureMessageConverters())
                .setControllerAdvice(RestControllerAdvice.class)
                .build();
    }

    private HttpMessageConverter<?>[] configureMessageConverters() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        objectMapper.registerModule(module);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Serialize dates as strings
        return new HttpMessageConverter<?>[]{converter};
    }

    @Test
    void shouldRetrievePrice() throws Exception {
        mockMvc.perform(
                        get("/prices")
                                .param("time", "2021-01-01T00:00:00")
                                .param("productId", "35455")
                                .param("brandId", "1")
                )
                .andExpect(status().isOk())
                .andDo(result -> assertModelDefinition(result.getResponse().getContentAsString()));

    }

    @Test
    void shouldRetrieveBadRequest_whenRequestsTriggersFieldValidation() throws Exception {
        mockMvc.perform(get("/prices"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRetrieveFieldValidationError_whenAFieldIsEmpty() throws Exception {
        mockMvc.perform(get("/prices"))
                .andDo(result -> {
                    var contentAsString = result.getResponse().getContentAsString();

                    assertThatJson(contentAsString).node("type").isEqualTo("validation");
                    assertNullConstraintValidation(contentAsString);
                });
    }
}
