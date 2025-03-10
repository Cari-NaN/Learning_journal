package com.example.learning_journal.repository;

import com.example.learning_journal.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUserId(Long userId);
}

