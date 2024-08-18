package com.meowmentor.themeservice.subtheme;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.meowmentor.themeservice.question.Question;
import com.meowmentor.themeservice.theme.Theme;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subtheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    @JsonBackReference
    private Theme theme;

    @OneToMany(mappedBy = "subtheme", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Question> questions;


    public void updateFrom(Subtheme updatedSubtheme) {
        this.title = updatedSubtheme.getTitle();
        // Вы можете обновить другие поля, если необходимо
        // Обновление `theme` и `questions` не выполняется для простого обновления
        // Если нужно обновить эти поля, убедитесь, что вы делаете это корректно
    }
}
