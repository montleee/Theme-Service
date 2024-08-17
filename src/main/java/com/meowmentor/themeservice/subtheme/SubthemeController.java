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
        return subthemeService.createSubtheme(dto);
    }


    @GetMapping
    public ResponseEntity<List<Subtheme>> getAllSubthemes() {
        return ResponseEntity.ok(subthemeService.getAllSubthemes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subtheme> getSubthemeById(@PathVariable Long id) {
        Optional<Subtheme> subtheme = subthemeService.getSubthemeById(id);
        return subtheme.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponseDto> updateSubtheme(@PathVariable Long id, @RequestBody Subtheme subtheme) {
//        try {
//            if (!subthemeService.getSubthemeById(id).isPresent()) {
//                return ResponseEntity.notFound().build();
//            }
//            subtheme.setId(id);
//            Subtheme updatedSubtheme = subthemeService.saveSubtheme(subtheme);
//            ApiResponseDto response = new ApiResponseDto("Subtheme updated successfully", HttpStatus.OK.value());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            ApiResponseDto response = new ApiResponseDto("Error updating subtheme", HttpStatus.INTERNAL_SERVER_ERROR.value());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteSubtheme(@PathVariable Long id) {
        try {
            if (!subthemeService.getSubthemeById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            subthemeService.deleteSubtheme(id);
            ApiResponseDto response = new ApiResponseDto("Subtheme deleted successfully", HttpStatus.NO_CONTENT.value());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ApiResponseDto response = new ApiResponseDto("Error deleting subtheme", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}