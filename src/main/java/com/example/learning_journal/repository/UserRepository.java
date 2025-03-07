package com.example.learning_journal.repository;

import com.example.learning_journal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
