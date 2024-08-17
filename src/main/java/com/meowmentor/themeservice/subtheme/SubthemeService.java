package com.meowmentor.themeservice.subtheme;


import com.meowmentor.themeservice.exceptions.ThemeNotFoundException;
import com.meowmentor.themeservice.exceptions.SubthemeNotFoundException;
import com.meowmentor.themeservice.subtheme.dto.CreateSubthemeDto;
import com.meowmentor.themeservice.theme.Theme;
import com.meowmentor.themeservice.theme.ThemeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubthemeService {

    private final SubthemeRepository subthemeRepository;
    private final ThemeRepository themeRepository;

    public List<Subtheme> getAllSubthemes() {
        log.info("Fetching all subthemes");
        List<Subtheme> subthemes = subthemeRepository.findAll();
        log.info("Found {} subthemes", subthemes.size());
        return subthemes;
    }

    public Optional<Subtheme> getSubthemeById(Long id) {
        log.info("Fetching subtheme with ID: {}", id);
        Optional<Subtheme> subtheme = subthemeRepository.findById(id);
        if (subtheme.isPresent()) {
            log.info("Subtheme found: {}", subtheme.get().getTitle());
        } else {
            log.warn("Subtheme with ID {} not found", id);
        }
        return subtheme;
    }

    public void createSubtheme(CreateSubthemeDto dto) {
        log.info("Creating subtheme with title: {} under theme ID: {}", dto.getTitle(), dto.getThemeId());

        Theme theme = themeRepository.findById(dto.getThemeId())
                .orElseThrow(() -> {
                    return new ThemeNotFoundException(dto.getThemeId());
                });

        Subtheme newSubtheme = new Subtheme();
        newSubtheme.setTitle(dto.getTitle());
        newSubtheme.setTheme(theme);

        subthemeRepository.save(newSubtheme);
        log.info("Created subtheme with title: '{}' under theme: '{}'", newSubtheme.getTitle(), theme.getTitle());
    }

    @Transactional
    public void updateSubtheme(Long id, Subtheme updatedSubtheme) {
        log.info("Updating subtheme with ID: {}", id);

        Subtheme existingSubtheme = subthemeRepository.findById(id)
                .orElseThrow(() -> {
                    return new SubthemeNotFoundException(id);
                });

        existingSubtheme.updateFrom(updatedSubtheme);
        subthemeRepository.save(existingSubtheme);
        log.info("Updated subtheme with ID: {}", id);
    }

    @Transactional
    public void deleteSubtheme(Long id) {
        log.info("Deleting subtheme with ID: {}", id);

        if (!subthemeRepository.existsById(id)) {
            log.warn("Subtheme with ID {} not found", id);
            throw new SubthemeNotFoundException(id);
        }

        subthemeRepository.deleteById(id);
        log.info("Deleted subtheme with ID: {}", id);
    }
}