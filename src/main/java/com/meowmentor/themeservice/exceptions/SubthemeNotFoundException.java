package com.meowmentor.themeservice.exceptions;

public class SubthemeNotFoundException extends RuntimeException {
    public SubthemeNotFoundException(Long subthemeId) {
        super("Subtheme with ID " + subthemeId + " not found");
    }
}