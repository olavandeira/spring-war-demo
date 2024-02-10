package com.example.demo.controller.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final Object parameters;
    public NotFoundException(String message, Object parameters) {
        super(message);
        this.parameters = parameters;
    }
}
