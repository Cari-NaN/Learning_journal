package com.example.learning_journal.controller;

import com.example.learning_journal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @RequestMapping("/create-user-with-topic")
    public ResponseEntity<String> createUserWithTopic() {
        userService.createUserWithTopics();
        return ResponseEntity.ok("User created with topics successfully!");
    }
}
