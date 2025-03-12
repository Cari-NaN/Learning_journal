package com.example.learning_journal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName; // Имя

    @Column(nullable = false)
    private String lastName; // Фамилия

    @Column(unique = true, nullable = false)
    private String username; // Логин

    @Column(unique = true, nullable = false)
    private String email; // E-Mail

    @Column(nullable = false)
    private String password; // Пароль

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "user_classes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private Set<LearningClass> classes = new HashSet<>();
}

