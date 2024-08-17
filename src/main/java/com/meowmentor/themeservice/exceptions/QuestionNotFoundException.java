package com.meowmentor.themeservice.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super("Question not found with id: " + id);
    }
}
