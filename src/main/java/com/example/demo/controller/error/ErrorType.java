package com.example.demo.controller.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {
    VALIDATION("validation"),
    NOT_FOUND("not_found");
    private final String value;
}
