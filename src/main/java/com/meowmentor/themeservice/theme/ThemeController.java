package com.meowmentor.themeservice.theme;


import com.meowmentor.themeservice.ApiResponseDto;
import com.meowmentor.themeservice.theme.dto.CreateThemeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;
    @GetMapping
    public ResponseEntity<List<Theme>> getAllThemes() {
        return ResponseEntity.ok(themeService.getAllThemes());
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> createTheme(@RequestBody CreateThemeDto dto) {

        themeService.createTheme(dto);
        ApiResponseDto response = new ApiResponseDto("Theme created successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Object> searchThemesByTitle(@PathVariable String title) {
        Optional<Theme> theme= themeService.findThemeByTitle(title);
        if (theme.isPresent()) {
            return ResponseEntity.ok(theme.get());
        } else {
            ApiResponseDto response = new ApiResponseDto("Theme not found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getThemeById(@PathVariable Long id) {

        Optional<Theme> theme = themeService.getThemeById(id);

        if (theme.isPresent()) {
            return ResponseEntity.ok(theme.get());
        } else {
            ApiResponseDto response = new ApiResponseDto("Theme not found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> getThemeById(@PathVariable Long id, @RequestBody Theme theme) {

        themeService.updateTheme(id,theme);
        var response = new ApiResponseDto("Question updated successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        ApiResponseDto response = new ApiResponseDto("Subtheme deleted successfully", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}