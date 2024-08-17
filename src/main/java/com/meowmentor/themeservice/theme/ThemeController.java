package com.meowmentor.themeservice.theme;


import com.meowmentor.themeservice.ApiResponseDto;
import com.meowmentor.themeservice.theme.dto.CreateThemeDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;
    @GetMapping
    public ResponseEntity<List<Theme>> getAllThemes() {
        try {
            List<Theme> themes = themeService.getAllThemes();
            return ResponseEntity.ok(themes);
        } catch (Exception e) {
            // Логирование ошибки и возврат ответа с ошибкой
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> createTheme(@RequestBody CreateThemeDto dto) {

          return themeService.createTheme(dto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteTheme(@PathVariable Long id) {
        try {
            boolean deleted = themeService.deleteTheme(id);
            if (deleted) {
                // Возвращаем успешный ответ
                ApiResponseDto response = new ApiResponseDto("Theme deleted successfully", HttpStatus.OK.value());
                return ResponseEntity.ok(response);
            } else {
                // Возвращаем ответ, если тема не найдена
                ApiResponseDto response = new ApiResponseDto("Theme not found", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            // Логирование ошибки и возврат ответа с ошибкой
            ApiResponseDto response = new ApiResponseDto("Error deleting theme", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
