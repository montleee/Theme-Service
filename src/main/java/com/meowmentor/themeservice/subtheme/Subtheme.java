package com.meowmentor.themeservice.subtheme;
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
    private Theme theme;

    @OneToMany(mappedBy = "subtheme")
    private Set<Question> questions;
}
