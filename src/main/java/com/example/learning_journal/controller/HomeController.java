package com.example.learning_journal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Открывает index.html из /templates
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Открывает login.html из /templates
    }
}

