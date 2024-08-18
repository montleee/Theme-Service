package com.meowmentor.themeservice.subtheme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CreateSubthemeDto {

    @NotEmpty(message = "Title should not be empty")
    private String title;

    @NotNull(message = "Theme ID should not be null")
    private Long themeId;
}