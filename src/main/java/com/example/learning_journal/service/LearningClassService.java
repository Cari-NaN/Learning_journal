package com.example.learning_journal.service;

import org.springframework.transaction.annotation.Transactional;
import com.example.learning_journal.model.LearningClass;
import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.LearningClassRepository;
import com.example.learning_journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class LearningClassService {
    private final LearningClassRepository learningClassRepository;
    private final UserRepository userRepository;

    // Получить список всех классов
    public List<LearningClass> getAllClasses() {
        return learningClassRepository.findAll();
    }

    // Получить класс по ID
    public LearningClass getClassById(Long id) {
        return learningClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Klass nicht gefunden!"));
    }

    // Создать или обновить класс (универсальный метод)
    public LearningClass saveOrUpdateClass(LearningClass learningClass) {
        if (learningClass.getId() != null) {
            LearningClass existingClass = getClassById(learningClass.getId());
            existingClass.setClassName(learningClass.getClassName());
            return learningClassRepository.save(existingClass);
        }
        return learningClassRepository.save(learningClass);
    }

    // Удалить класс
    public void deleteClass(Long id) {
        if (!learningClassRepository.existsById(id)) {
            throw new RuntimeException("Klass nicht gefunden!");
        }
        learningClassRepository.deleteById(id);
    }

    //  Назначить ученика в класс
    @Transactional
    public void assignStudentToClass(Long classId, Long userId) {
        LearningClass learningClass = getClassById(classId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));

        learningClass.getStudents().add(user);
        user.getClasses().add(learningClass);

        userRepository.saveAndFlush(user); // Сбрасываем кэш
        learningClassRepository.saveAndFlush(learningClass); // Сбрасываем кэш
    }

    //  Удалить ученика из класса
    public void removeStudentFromClass(Long classId, Long userId) {
        LearningClass learningClass = getClassById(classId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden!"));

        // Удаляем связь в обе стороны
        learningClass.getStudents().remove(user);
        user.getClasses().remove(learningClass);

        userRepository.save(user);
        learningClassRepository.save(learningClass);
    }

    //  Получить всех учеников класса
    public List<User> getStudentsInClass(Long classId) {
        LearningClass learningClass = getClassById(classId);
        return List.copyOf(learningClass.getStudents());
    }

    public Optional<LearningClass> getClassByIdOptional(Long id) {
        return learningClassRepository.findById(id);
    }

    public Optional<User> getUserByIdOptional(Long id) {
        return userRepository.findById(id);
    }
}



