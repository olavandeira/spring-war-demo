package com.example.demo.controller.error;

import com.example.demo.controller.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice
@Slf4j
public class ApplicationControllerAdvice {
    private final ObjectMapper mapper;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleException(MethodArgumentNotValidException ex) {
        var body = new ErrorResponseDto()
                .setType(ErrorType.VALIDATION.getValue())
                .setErrors(buildFieldValidationResponseBody(ex));
        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleException(NotFoundException ex) {
        var body = new ErrorResponseDto()
                .setType(ErrorType.NOT_FOUND.getValue())
                .setErrors(Collections.singletonList(
                        new ErrorDto()
                                .setMessage(ex.getMessage())
                                .setRequestParams(mapper.convertValue(ex.getParameters(), Map.class)
                                )));
        return ResponseEntity
                .status(404)
                .body(body);
    }

    private List<ErrorDto> buildFieldValidationResponseBody(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorDto()
                        .setCode(fieldError.getCode())
                        .setMessage(fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .setField(fieldError.getField()))
                .collect(Collectors.toList());
    }
}
