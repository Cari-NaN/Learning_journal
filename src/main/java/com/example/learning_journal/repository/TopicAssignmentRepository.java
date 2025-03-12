package com.example.learning_journal.repository;

import com.example.learning_journal.model.TopicAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TopicAssignmentRepository extends JpaRepository<TopicAssignment, Long> {
    List<TopicAssignment> findByUserId(Long userId);
}

