package com.meowmentor.themeservice.theme;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.meowmentor.themeservice.subtheme.Subtheme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String themeImage; // Переименуйте поле

    @Embedded
    private Award award;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Subtheme> subthemes;
}
