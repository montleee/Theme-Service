package com.meowmentor.themeservice.question;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.meowmentor.themeservice.question.dto.components.Difficulty;
import com.meowmentor.themeservice.subtheme.Subtheme;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @ElementCollection
    @CollectionTable(name = "answers", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "answer")
    private List<String> answers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @ManyToOne
    @JoinColumn(name = "subtheme_id", nullable = false)
    @JsonBackReference
    private Subtheme subtheme;

    public void updateFrom(Question updatedQuestion) {
        this.question = updatedQuestion.getQuestion();
        this.answers = updatedQuestion.getAnswers();
        this.difficulty = updatedQuestion.getDifficulty();
        this.subtheme = updatedQuestion.getSubtheme(); // Учтите возможные обновления связанного объекта
    }
}
