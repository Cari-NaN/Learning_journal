package com.example.learning_journal.repository;

import com.example.learning_journal.model.Topic;
import com.example.learning_journal.model.User;
import com.example.learning_journal.model.UserTopic;
import org.springframework.data.jpa.repository.JpaRepository;

//→ Durch JpaRepository werden Methoden wie save(), findById() oder delete() automatisch bereitgestellt.
//Repository für CRUD-Operationen erstellen
//Damit du prüfen kannst, ob ein UserTopic-Eintrag bereits existiert
public interface UserTopicRepository extends JpaRepository<UserTopic, Long> {
    boolean existsByUserAndTopic(User user, Topic topic);
}
