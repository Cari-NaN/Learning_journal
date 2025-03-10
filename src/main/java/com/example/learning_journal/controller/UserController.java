package com.example.learning_journal.controller;

import com.example.learning_journal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//→ Mit JPA können Datenbankzugriffe deklarativ durch Repository-Interfaces erfolgen, ohne explizite SQL-Statements zu schreiben.

//API Rest-Controller for user operations

//Controller for creating a user with topics using UserService and Spring MVC annotations.
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
//Service injection for UserService in the controller constructor
public class UserController {
//Constructor injection for UserService
    private final UserService userService;
//Post-Mapping for creating a user with topics
    @RequestMapping("/create-user-with-topic")
    public ResponseEntity<String> createUserWithTopic() {
        userService.createUserWithTopics();
        return ResponseEntity.ok("User created with topics successfully!");
    }

    //Post-Mapping for assigning a user to a topic using UserService and Spring MVC annotations.
    @PostMapping("/{userId}/topics/{topicId}")
    public ResponseEntity<String> assignUserToTopic(@PathVariable Long userId, @PathVariable Long topicId) {
        userService.assignUserToTopic(userId, topicId);
        return ResponseEntity.ok("User wurde dem Topic zugewiesen!");
    }
    @GetMapping()//Aufruf der Thymeleaf HTML Seite users.html
    public String users() {
        return "users";
    }
}
