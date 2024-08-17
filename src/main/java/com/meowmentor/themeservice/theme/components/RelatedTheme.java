package com.meowmentor.themeservice.theme.components;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class RelatedTheme {
    private String image;
    private String title;
}