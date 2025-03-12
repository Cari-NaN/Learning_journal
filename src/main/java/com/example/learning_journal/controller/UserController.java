package com.example.learning_journal.controller;

import com.example.learning_journal.model.Topic;
import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.TopicRepository;
import com.example.learning_journal.repository.UserRepository;
import com.example.learning_journal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private final TopicRepository   topicRepository;
    private final UserRepository userRepository;

    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        System.out.println("Anfrage für Registrierungsseite erhalten...");

        // Überprüfen, ob Topics bereits vorhanden sind
        long topicCount = topicRepository.count();
        System.out.println("Gefundene Topics in der DB: " + topicCount);

        if (topicCount == 0) {
            System.out.println("Keine Topics gefunden, speichere Standard-Topics...");
            topicRepository.save(new Topic("Software Engineering", "Learn Programming", 60));
            topicRepository.save(new Topic("Data Science", "Learn Data Analysis", 90));
            topicRepository.save(new Topic("Machine Learning", "Learn Machine Learning", 120));
            topicRepository.save(new Topic("Artificial Intelligence", "Learn AI", 150));

            topicCount = topicRepository.count();
            System.out.println("Nach Speicherung: " + topicCount + " Topics gefunden.");
        }

        List<Topic> topics = topicRepository.findAll();
        System.out.println("Lade folgende Topics: " + topics);

        model.addAttribute("user", new User());
        model.addAttribute("topics", topics);

        return "register";
    }
//Post-Mapping for creating a new user
    //Post-Mapping for registering a user
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @RequestParam List<Long> topicIds) {
        Set<Topic> selectedTopics = new HashSet<>(topicRepository.findAllById(topicIds)); // Get topics from topicIds.
        user.setTopics((List<Topic>) selectedTopics); // Set topics to the user.
        userRepository.save(user);
        return "redirect:/users"; // Redirect to user list.
    }
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
