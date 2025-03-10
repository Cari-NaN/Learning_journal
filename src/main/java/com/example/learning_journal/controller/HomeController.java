package com.example.learning_journal.controller;

import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller//Spring erkennt dies als Web-Controller.
@RequiredArgsConstructor//Wir brauchen die UserRepository für die Konstruktor-Injection.
public class HomeController {

    private final UserRepository userRepository;

       @GetMapping("/")//Wenn der Benutzer / aufruft (z. B. http://localhost:8080), wird die index.html-Seite geladen.
    public String home() {
        return "index";//Spring sucht nach einer index.html-Datei in src/main/resources/templates/.
    }
    @GetMapping("database-view")//Diese Methode wird aufgerufen, wenn der Benutzer auf "H2 Datenbank anzeigen" klickt.
    public String showDatabase(Model model) {
        List<User> users = userRepository.findAll();//Holt alle Benutzer aus der H2-Datenbank.
        model.addAttribute("users", users);//Überträgt die Benutzerliste an die database-view.html Seite.
        return "database-view";//Spring sucht nach einer database-view.html-Datei in src/main/resources/templates/.
        // Diese Seite kann über http://localhost:8080/database-view aufrufen werden.
        // Diese Seite kann verwendet werden, um die Datenbank zu sehen.
        // Die Datenbank kann in der application.properties-Datei
    }
}
