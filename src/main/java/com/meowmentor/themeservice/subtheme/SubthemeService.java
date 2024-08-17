package com.meowmentor.themeservice.subtheme;

import com.meowmentor.themeservice.ApiResponseDto;
import com.meowmentor.themeservice.exceptions.ThemeNotFoundException;
import com.meowmentor.themeservice.subtheme.dto.CreateSubthemeDto;
import com.meowmentor.themeservice.theme.Theme;
import com.meowmentor.themeservice.theme.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubthemeService {


    private final SubthemeRepository subthemeRepository;
    private final ThemeRepository themeRepository;

    public ResponseEntity<ApiResponseDto> createSubtheme(CreateSubthemeDto dto) {
        // Найти Theme по ID
        Theme theme = themeRepository.findById(dto.getThemeId())
                .orElseThrow(() -> new ThemeNotFoundException(dto.getThemeId()));

        // Создать новый Subtheme
        Subtheme newSubtheme = new Subtheme();
        newSubtheme.setTitle(dto.getTitle());
        newSubtheme.setTheme(theme);

        // Сохранить Subtheme
        subthemeRepository.save(newSubtheme);

        // Логирование
        System.out.println("Created subtheme with title: " + newSubtheme.getTitle() + " under theme: " + theme.getTitle());

        // Возвращаем успешный ответ
        ApiResponseDto response = new ApiResponseDto("Subtheme created successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public List<Subtheme> getAllSubthemes() {
        return subthemeRepository.findAll();
    }

    public Optional<Subtheme> getSubthemeById(Long id) {
        return subthemeRepository.findById(id);
    }



    public void deleteSubtheme(Long id) {
        subthemeRepository.deleteById(id);
    }
}