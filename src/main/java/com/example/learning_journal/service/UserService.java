package com.example.learning_journal.service;

import com.example.learning_journal.model.LearningClass;
import com.example.learning_journal.model.Role;
import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.LearningClassRepository;
import com.example.learning_journal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LearningClassRepository learningClassRepository;

    // Регистрация нового пользователя
    public User registerUser(String firstName, String lastName, String username, String email, String password, String confirmPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("❌ Der Nutzername ist bereits vergeben!");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("❌ Diese E-Mail-Adresse wird bereits verwendet!");
        }

        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("❌ Neues Passwort und Bestätigung stimmen nicht überein!");
        }

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password)) // Хешируем пароль
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    // Аутентификация
    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    // Получить список всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Получить пользователя по ID (бросает исключение, если не найден)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Benutzer nicht gefunden!"));
    }

    // 🔹 Новый метод: получить пользователя по ID (без исключения)
    public Optional<User> getUserByIdOptional(Long id) {
        return userRepository.findById(id);
    }

    // Обновить данные пользователя
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);

        if (updatedUser.getFirstName() != null) existingUser.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null) existingUser.setLastName(updatedUser.getLastName());
        if (updatedUser.getUsername() != null) existingUser.setUsername(updatedUser.getUsername());
        if (updatedUser.getEmail() != null) existingUser.setEmail(updatedUser.getEmail());

        // Проверяем, изменил ли пользователь пароль
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    // Удалить пользователя
    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("❌ Ein Administrator kann nicht gelöscht werden!");
        }
        userRepository.deleteById(id);
    }

    public void changePassword(String username, String oldPassword, String newPassword, String confirmPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("❌ Benutzer nicht gefunden!"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Altes Passwort falsch eingegeben");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("❌ Neues Passwort und Bestätigung stimmen nicht überein!");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    // Получить пользователя по username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden!"));
    }

    public void addUserToClass(String username, Long classId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        LearningClass learningClass = learningClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!user.getClasses().contains(learningClass)) {
            user.getClasses().add(learningClass);
            userRepository.save(user);
        }
    }

    public void removeUserFromClass(String username, Long classId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        LearningClass learningClass = learningClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        user.getClasses().remove(learningClass);
        userRepository.save(user);
    }
}