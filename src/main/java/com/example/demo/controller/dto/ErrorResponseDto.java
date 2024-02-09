package com.example.demo.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ErrorResponseDto {
    private String type;
    private List<ErrorDto> errors;
}
