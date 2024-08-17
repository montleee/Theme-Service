package com.meowmentor.themeservice.theme;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.meowmentor.themeservice.subtheme.Subtheme;

import com.meowmentor.themeservice.theme.components.Award;
import com.meowmentor.themeservice.theme.components.RelatedTheme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.List;
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

    private String themeImage;

    @Embedded
    private Award award;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Subtheme> subthemes;

    @ElementCollection
    @CollectionTable(name = "related_theme", joinColumns = @JoinColumn(name = "theme_id"))
    private List<RelatedTheme> relatedThemes;
}
