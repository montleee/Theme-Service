package com.meowmentor.themeservice.exceptions;

public class NoQuestionsFoundException extends RuntimeException {

    public NoQuestionsFoundException(Long subthemeId) {
        super("No questions found for subtheme ID: " + subthemeId);
    }

    public NoQuestionsFoundException() {
        super("No questions found in the repository.");
    }
}