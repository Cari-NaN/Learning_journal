package com.example.learning_journal.service;

import com.example.learning_journal.model.LearningClass;
import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.LearningClassRepository;
import com.example.learning_journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LearningClassService {
    private final LearningClassRepository learningClassRepository;
    private final UserRepository userRepository;

    // Erstellen einer neuen Klasse (nur für Admin)
    public LearningClass createClass(String className) {
        LearningClass learningClass = LearningClass.builder()
                .className(className)
                .build();
        return learningClassRepository.save(learningClass);
    }

    // Anhängen eines Benutzers an eine Klasse
    public void enrollUserToClass(Long userId, Long classId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<LearningClass> classOpt = learningClassRepository.findById(classId);

        if (userOpt.isPresent() && classOpt.isPresent()) {
            User user = userOpt.get();
            LearningClass learningClass = classOpt.get();
            user.getClasses().add(learningClass);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Kein Benutzer oder keine Klasse gefunden!");
        }
    }
}

