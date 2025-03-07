package com.example.learning_journal.service;

import com.example.learning_journal.model.Topic;
import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.TopicRepository;
import com.example.learning_journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TopicRepository roleRepository;

    public void createUserWithTopics() {
        User user = new User();
        user.setUsername("JohnDoe");

        Topic topic = new Topic();
        topic.setTopicName("Spring Boot Basics");
        topic.setUser(user);

        Topic topic2 = new Topic();
        topic2.setTopicName("Lombok in Java");
        topic2.setUser(user);

        user.setTopics(List.of(topic, topic2));

        userRepository.save(user);
    }

}
