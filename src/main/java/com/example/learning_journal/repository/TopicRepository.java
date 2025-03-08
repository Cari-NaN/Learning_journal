package com.example.learning_journal.repository;

import com.example.learning_journal.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

//Topic Repository interface for JPA operations on Topic entity

// Import annotations for Entity, Table, Getters, Setters, Builder, AllArgsConstructor, andNoArgsConstructor
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
