package com.meowmentor.themeservice.theme;

import com.meowmentor.themeservice.theme.dto.CreateThemeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;



    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }
    public Theme createTheme(CreateThemeDto dto) {
        Theme theme = new Theme();
        theme.setTitle(dto.getTitle());
        theme.setDescription(dto.getDescription());

        String image = dto.getImage();
        if (image == null || image.trim().isEmpty()) {
            image = "default-image.png";
        }
        theme.setImage(image);

        // Здесь можно обработать связанные темы, если это нужно
        // Например, использовать themeRepository.findAllById(dto.getRelatedThemesIds()) и установить связанные темы

        return themeRepository.save(theme);
    }


}