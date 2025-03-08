package com.example.learning_journal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//1. User-Entity (One-Seite der Beziehung)

//Erklärung:
//
//@OneToMany(mappedBy = "user"): Das mappedBy gibt an, dass user das Feld in Topic ist, das diese Beziehung verwaltet.
//cascade = CascadeType.ALL: Dadurch werden Änderungen am User auch auf die Topics angewendet (z. B. wenn ein User gelöscht wird, werden auch seine Topics entfernt).
//orphanRemoval = true: Stellt sicher, dass Topics ohne User gelöscht werden.


// User Entity
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// Role Enum
public class User {
    // User ID Column (Primary Key)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Username Column
    @Column(nullable = false, unique = true)
    private String username;

    // Password Column
    @Column(nullable = false)
    private String password;

    // Role Column
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // OneToMany Relationship with LearningClasses Entity
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningClasses> learningClasses;

    // ManyToMany mit Zwischenklasse (Empfohlen, wenn zusätzliche Attribute benötigt werden)
    //Falls du z. B. speichern möchtest, wann ein User sich einem Topic angeschlossen hat, brauchst du eine eigene Entity für die Beziehung.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserTopic> userTopics;

    public void setTopics(List<Topic> topic) {
    }
}
