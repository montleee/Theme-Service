package com.meowmentor.themeservice.exceptions;

import com.meowmentor.themeservice.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SubthemeNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleSubthemeNotFoundException(SubthemeNotFoundException ex) {
        log.error("SubthemeNotFoundException: ", ex);
        ApiResponseDto response = new ApiResponseDto(
                "Subtheme not found", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ThemeNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleThemeNotFoundException(ThemeNotFoundException ex) {
        log.error("ThemeNotFoundException: ", ex);
        ApiResponseDto response = new ApiResponseDto(
                "Theme not found", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleGeneralException(Exception ex) {
        log.error("General Exception: ", ex);
        ApiResponseDto response = new ApiResponseDto(
                "An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}