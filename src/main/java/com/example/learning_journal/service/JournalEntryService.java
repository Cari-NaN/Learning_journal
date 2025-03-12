package com.example.learning_journal.service;

import com.example.learning_journal.model.JournalEntry;
import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.JournalEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalEntryService {
    private final JournalEntryRepository journalEntryRepository;

    // Einen neuen Datensatz erstellen
    public JournalEntry createEntry(User user, String content) {
        JournalEntry entry = JournalEntry.builder()
                .user(user)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();
        return journalEntryRepository.save(entry);
    }

    // Alle Benutzerdatensätze abrufen
    public List<JournalEntry> getUserEntries(Long userId) {
        return journalEntryRepository.findByUserId(userId);
    }
}

