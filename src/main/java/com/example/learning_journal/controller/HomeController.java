package com.example.learning_journal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//Spring erkennt dies als Web-Controller.
public class HomeController {

    @GetMapping("/")//Wenn der Benutzer / aufruft (z. B. http://localhost:8080), wird die index.html-Seite geladen.
    public String home() {
        return "index";//Spring sucht nach einer index.html-Datei in src/main/resources/templates/.
    }
}
