package com.meowmentor.themeservice.theme;

import com.meowmentor.themeservice.exceptions.ThemeNotFoundException;
import com.meowmentor.themeservice.theme.components.Award;
import com.meowmentor.themeservice.theme.components.RelatedTheme;
import com.meowmentor.themeservice.theme.dto.CreateThemeDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public List<Theme> getAllThemes() {
        log.info("Fetching all themes");
        List<Theme> themes = themeRepository.findAll();
        log.info("Found {} themes", themes.size());
        return themes;
    }

    public Optional<Theme> getThemeById(Long id) {
        log.info("Fetching theme with ID: {}", id);
        Optional<Theme> theme = themeRepository.findById(id);
        if (theme.isPresent()) {
            log.info("Theme found: {}", theme.get().getTitle());
        } else {
            log.warn("Theme with ID {} not found", id);
        }
        return theme;
    }

    public void createTheme(CreateThemeDto dto) {
        log.info("Creating new theme with title: {}", dto.getTitle());

        Theme theme = new Theme();
        theme.setTitle(dto.getTitle());
        theme.setDescription(dto.getDescription());

        String image = defaultIfEmpty(dto.getImage(), "default-image.png");
        theme.setThemeImage(image);

        Award award = new Award();
        award.setAwardDescription(defaultIfEmpty(dto.getAwardDescription(), "Default award description"));
        award.setAwardImage(defaultIfEmpty(dto.getAwardImage(), "default-award-image.png"));
        award.setAwardTitle(defaultIfEmpty(dto.getAwardTitle(), "Default award title"));
        theme.setAward(award);

        List<Theme> foundThemes = themeRepository.findAllById(dto.getRelatedThemesIds());

        List<RelatedTheme> relatedThemes = foundThemes.stream()
                .map(this::convertToRelatedTheme)
                .collect(Collectors.toList());

        theme.setRelatedThemes(relatedThemes.isEmpty() ? Collections.emptyList() : relatedThemes);

        themeRepository.save(theme);
        log.info("Theme with title '{}' created successfully", theme.getTitle());
    }

    public void deleteTheme(Long id) {
        log.info("Deleting theme with ID: {}", id);

        if (!themeRepository.existsById(id)) {
            log.warn("Theme with ID {} not found", id);
            throw new ThemeNotFoundException(id);
        }

        themeRepository.deleteById(id);
        log.info("Deleted theme with ID: {}", id);
    }

    public void updateTheme(Long id, Theme updatedTheme) {
        log.info("Updating theme with ID: {}", id);

        Theme existingTheme = themeRepository.findById(id)
                .orElseThrow(() -> {
                    return new ThemeNotFoundException(id);
                });

        existingTheme.updateFrom(updatedTheme);
        themeRepository.save(existingTheme);
        log.info("Updated theme with ID: {}", id);
    }

    private String defaultIfEmpty(String value, String defaultValue) {
        return (value == null || value.trim().isEmpty()) ? defaultValue : value;
    }

    private RelatedTheme convertToRelatedTheme(Theme theme) {
        log.info("Converting theme '{}' to related theme", theme.getTitle());
        RelatedTheme relatedTheme = new RelatedTheme();
        relatedTheme.setImage(theme.getThemeImage());
        relatedTheme.setTitle(theme.getTitle());
        return relatedTheme;
    }
}