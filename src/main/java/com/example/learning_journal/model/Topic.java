package com.example.learning_journal.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

//2. Topic-Entity (Many-Seite der Beziehung)

//Erklärung:
//
//@ManyToOne: Jede Topic gehört genau einem User.
//@JoinColumn(name = "user_id"): Die user_id-Spalte in der Topic-Tabelle referenziert den User.

@Entity
@Table(name = "topics")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Topic {
    // Auto-generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Topic Name Column
    @Column(nullable = false)
    private String topicName;

    // Topic Description Column
    @Column(nullable = false)
    private String description;

    // Topic Duration Column  - in minutes
    @Column(nullable = false)
    private int duration;

    // OneToMany Relationship with User Entity
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //OneToMany zur Zwischenklasse UserTopic (Empfohlen, wenn zusätzliche Attribute benötigt werden)
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserTopic> userTopics;
}
