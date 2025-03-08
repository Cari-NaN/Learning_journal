package com.example.learning_journal.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "journal_entries")
@Getter
@Setter
public class JournalEntry {

    @Id
    private Long id;

}
