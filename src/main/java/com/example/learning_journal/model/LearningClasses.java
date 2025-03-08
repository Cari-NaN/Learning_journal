package com.example.learning_journal.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Class")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LearningClasses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String className;

    @Column(nullable = false)
    private int numberOfStudents;

    // OneToMany Relationship with User Entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
