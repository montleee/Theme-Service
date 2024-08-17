package com.meowmentor.themeservice.question.dto;

import com.meowmentor.themeservice.question.Difficulty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.List;

@Data
public class CreateQuestionDto {

    @NotEmpty(message = "Question should not be empty")
    private String question;

    @NotNull(message = "Answers should not be null")
    @NotEmpty(message = "Answers list should not be empty")
    private List<@NotEmpty(message = "Answer should not be empty") String> answers;

    @NotNull(message = "Subtheme ID should not be null")
    private Long subthemeId;

    @NotNull(message = "Difficulty should not be null")
    private Difficulty difficulty;
}
