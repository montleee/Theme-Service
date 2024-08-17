package com.meowmentor.themeservice.theme;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Award {
    private String awardTitle;
    private String awardImage;
    private String awardDescription;

    public Award() {}

    public Award(String title, String image, String description) {
        this.awardTitle = title;
        this.awardImage = image;
        this.awardDescription = description;
    }
}