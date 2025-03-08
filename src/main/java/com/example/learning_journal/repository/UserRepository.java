package com.example.learning_journal.repository;

import com.example.learning_journal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
//3. UserRepository


// Import annotations for Entity, Table, Getters, Setters, Builder, AllArgsConstructor, andNoArgsConstructor
public interface UserRepository extends JpaRepository<User, Long> {
}
