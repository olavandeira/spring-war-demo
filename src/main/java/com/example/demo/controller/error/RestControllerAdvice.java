package com.example.demo.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestControllerAdvice
@Slf4j
public class RestControllerAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDto> handleException(MethodArgumentNotValidException ex) {
        var body = new ErrorResponseDto()
                .setType("validation")
                .setErrors(buildFieldValidationResponseBody(ex));
        return ResponseEntity
                .badRequest()
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
