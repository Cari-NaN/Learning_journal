package com.example.learning_journal.controller;


import com.example.learning_journal.model.Role;
import com.example.learning_journal.model.Topic;
import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.TopicRepository;
import com.example.learning_journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    //Lädt die Registrierungsseite mit einem leeren Benutzerobjekt.
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Initialisiert ein neues User-Objekt.
        return "register"; // Spring sucht nach einer register.html-Datei in src/main/resources/templates/.
    }
    //Speichert den Benutzer in der Datenbank und leitet weiter auf die Login-Seite.
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setTopics(List.of()); // Initialisiert eine leere Liste von Topics.
        user.setRole(Role.USER); // Setzt die Role auf USER.
        userRepository.save(user); // Speichert den neuen Benutzer in der H2-Datenbank.
        return "redirect:/users"; // Weiterleitung auf die Benutzerliste.
    }

}
