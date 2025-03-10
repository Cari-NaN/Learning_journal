package com.example.learning_journal.repository;

import com.example.learning_journal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//→ Durch JpaRepository werden Methoden wie save(), findById() oder delete() automatisch bereitgestellt.
//Repository für CRUD-Operationen erstellen
//3. UserRepository


// Import annotations for Entity, Table, Getters, Setters, Builder, AllArgsConstructor, andNoArgsConstructor
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(int id);
}
