package com.meowmentor.themeservice.exceptions;

public class ThemeNotFoundException extends RuntimeException {

    // Конструктор с сообщением об ошибке
    public ThemeNotFoundException(Long themeId) {
        super("Theme with ID " + themeId + " not found.");
    }

    // Конструктор с сообщением об ошибке и причиной
    public ThemeNotFoundException(Long themeId, Throwable cause) {
        super("Theme with ID " + themeId + " not found.", cause);
    }
}
