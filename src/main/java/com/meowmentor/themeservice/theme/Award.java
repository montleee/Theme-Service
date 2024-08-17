package com.meowmentor.themeservice.theme;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Award {
    private String title;
    private String image;
    private String description;

    public Award() {}

    public Award(String title, String image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
    }
}