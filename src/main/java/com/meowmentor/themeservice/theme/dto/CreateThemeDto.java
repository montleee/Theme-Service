package com.meowmentor.themeservice.theme.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateThemeDto {
    @NotEmpty(message = "Title should not be empty")
    private String title;

    @NotEmpty(message = "Description should not be empty")
    @Size(max = 1000, message = "Description can be up to 1000 characters long")
    private String description;

    private String image;
//    private List<Long> relatedThemesIds;
}
