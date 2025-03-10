package com.example.learning_journal.repository;

import com.example.learning_journal.model.LearningClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningClassRepository extends JpaRepository<LearningClass, Long> {
}

