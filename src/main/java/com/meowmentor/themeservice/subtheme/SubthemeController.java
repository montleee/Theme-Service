package com.meowmentor.themeservice.subtheme;


import com.meowmentor.themeservice.ApiResponseDto;
import com.meowmentor.themeservice.subtheme.dto.CreateSubthemeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subthemes")
@RequiredArgsConstructor
public class SubthemeController {

    private final SubthemeService subthemeService;


    @PostMapping
    public ResponseEntity<ApiResponseDto> createSubtheme(@RequestBody CreateSubthemeDto dto) {
        subthemeService.createSubtheme(dto);
        ApiResponseDto response = new ApiResponseDto("Subtheme created successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Subtheme>> getAllSubthemes() {
        return ResponseEntity.ok(subthemeService.getAllSubthemes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubthemeById(@PathVariable Long id) {
        Optional<Subtheme> subtheme = subthemeService.getSubthemeById(id);

        if (subtheme.isPresent()) {
            return ResponseEntity.ok(subtheme.get());
        } else {
            ApiResponseDto response = new ApiResponseDto("Subtheme not found", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> updateQuestion(@PathVariable Long id, @RequestBody Subtheme subtheme) {

        subthemeService.updateSubtheme(id,subtheme);
        var response = new ApiResponseDto("Question updated successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteSubtheme(@PathVariable Long id) {
        subthemeService.deleteSubtheme(id);
        ApiResponseDto response = new ApiResponseDto("Subtheme deleted successfully", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}