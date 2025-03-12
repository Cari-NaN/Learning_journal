package com.example.learning_journal.service;

import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Registrierung neuer Benutzer
    public User registerUser(String firstName, String lastName, String username, String email, String password, String confirmPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Der Benutzername existiert bereits!");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Diese E-Mail wird bereits verwendet!");
        }

        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwörter stimmen nicht überein!");
        }

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password)) // Хешируем пароль
                .role(com.example.learning_journal.model.Role.USER)
                .build();

        return userRepository.save(user);
    }

    // Authentifizierung
    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
}

