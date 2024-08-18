package com.meowmentor.themeservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDto {
    private String message;
    private int statusCode;
}