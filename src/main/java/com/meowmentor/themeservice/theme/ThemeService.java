package com.meowmentor.themeservice.theme;

import com.meowmentor.themeservice.theme.dto.CreateThemeDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
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
    public Theme createTheme(CreateThemeDto dto) {
        log.info("Creating theme with DTO: {}", dto);

        Theme theme = new Theme();
        theme.setTitle(dto.getTitle());
        theme.setDescription(dto.getDescription());

        // Установка изображения по умолчанию, если не указано
        String image = defaultIfEmpty(dto.getImage(), "default-image.png");
        theme.setThemeImage(image);
        log.info("Set theme image to: {}", image);

        // Создание и установка объекта Award с значениями по умолчанию
        Award award = new Award();
        award.setAwardDescription(defaultIfEmpty(dto.getAwardDescription(), "Default award description"));
        award.setAwardImage(defaultIfEmpty(dto.getAwardImage(), "default-award-image.png"));
        award.setAwardTitle(defaultIfEmpty(dto.getAwardTitle(), "Default award title"));
        theme.setAward(award);
        log.info("Award details set - description: {}, image: {}, title: {}",
                award.getAwardDescription(), award.getAwardImage(), award.getAwardTitle());

        // Логирование ID связанных тем
        log.info("Received related theme IDs: {}", dto.getRelatedThemesIds());

        // Получение связанных тем
        List<Theme> foundThemes = themeRepository.findAllById(dto.getRelatedThemesIds());
        log.info("Found themes: {}", foundThemes);

        // Преобразование найденных тем в RelatedTheme
        List<RelatedTheme> relatedThemes = foundThemes.stream()
                .map(this::convertToRelatedTheme)
                .collect(Collectors.toList());
        log.info("Converted to related themes: {}", relatedThemes);

        // Установка связанных тем
        theme.setRelatedThemes(relatedThemes.isEmpty() ? Collections.emptyList() : relatedThemes);
        log.info("Final related themes set: {}", theme.getRelatedThemes());

        // Сохранение темы
        try {
            Theme savedTheme = themeRepository.save(theme);
            log.info("Saved theme with ID: {}", savedTheme.getId());
            return savedTheme;
        } catch (Exception e) {
            log.error("Error saving theme: {}", e.getMessage(), e);
            throw new RuntimeException("Error saving theme", e);
        }

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