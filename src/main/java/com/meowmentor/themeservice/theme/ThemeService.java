package com.meowmentor.themeservice.theme;

import com.meowmentor.themeservice.ApiResponseDto;
import com.meowmentor.themeservice.theme.components.Award;
import com.meowmentor.themeservice.theme.components.RelatedTheme;
import com.meowmentor.themeservice.theme.dto.CreateThemeDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;



    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }


    public ResponseEntity<ApiResponseDto> createTheme(CreateThemeDto dto) {


        Theme theme = new Theme();
        theme.setTitle(dto.getTitle());
        theme.setDescription(dto.getDescription());

        // Установка изображения по умолчанию, если не указано
        String image = defaultIfEmpty(dto.getImage(), "default-image.png");
        theme.setThemeImage(image);


        // Создание и установка объекта Award с значениями по умолчанию
        Award award = new Award();
        award.setAwardDescription(defaultIfEmpty(dto.getAwardDescription(), "Default award description"));
        award.setAwardImage(defaultIfEmpty(dto.getAwardImage(), "default-award-image.png"));
        award.setAwardTitle(defaultIfEmpty(dto.getAwardTitle(), "Default award title"));
        theme.setAward(award);


        // Получение связанных тем
        List<Theme> foundThemes = themeRepository.findAllById(dto.getRelatedThemesIds());

        // Преобразование найденных тем в RelatedTheme
        List<RelatedTheme> relatedThemes = foundThemes.stream()
                .map(this::convertToRelatedTheme)
                .collect(Collectors.toList());


        // Установка связанных тем
        theme.setRelatedThemes(relatedThemes.isEmpty() ? Collections.emptyList() : relatedThemes);

        themeRepository.save(theme);
        ApiResponseDto response = new ApiResponseDto("Theme created successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    public boolean deleteTheme(Long id) {
        if (themeRepository.existsById(id)) {
            themeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    private String defaultIfEmpty(String value, String defaultValue) {
        return (value == null || value.trim().isEmpty()) ? defaultValue : value;
    }
    private RelatedTheme convertToRelatedTheme(Theme theme) {
        RelatedTheme relatedTheme = new RelatedTheme();
        relatedTheme.setImage(theme.getThemeImage()); // Используйте соответствующие поля
        relatedTheme.setTitle(theme.getTitle());
        return relatedTheme;
    }
}