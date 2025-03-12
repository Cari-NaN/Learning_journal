package com.example.learning_journal.controller;


import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepository userRepository;
    //Lädt die Registrierungsseite mit einem leeren Benutzerobjekt.
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Initialisiert ein neues User-Objekt.
        return "register"; // Spring sucht nach einer register.html-Datei in src/main/resources/templates/.
    }
    //Speichert den Benutzer in der Datenbank und leitet weiter auf die Login-Seite.
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userRepository.save(user); // Speichert den neuen Benutzer in der H2-Datenbank.
        return "redirect:/users"; // Weiterleitung auf die Benutzerliste.
    }
}
