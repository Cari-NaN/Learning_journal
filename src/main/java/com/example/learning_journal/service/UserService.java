package com.example.learning_journal.service;

import com.example.learning_journal.model.Topic;
import com.example.learning_journal.model.User;
import com.example.learning_journal.model.UserTopic;
import com.example.learning_journal.repository.TopicRepository;
import com.example.learning_journal.repository.UserRepository;
import com.example.learning_journal.repository.UserTopicRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


// Rest Controller and RequestMapping omitted for brevity
@Service
@RequiredArgsConstructor
// Service class for user operations
public class UserService {

    private final TopicRepository topicRepository;
    // Injecting repositories
    private final UserRepository userRepository;
    private final UserTopicRepository userTopicRepository;

    // Method to create a user with topics
    public void createUserWithTopics() {
        User user = new User();
        user.setUsername("JohnDoe");

        Topic topic = new Topic();
        topic.setTopicName("Spring Boot Basics");
        topic.setUser(user);

        Topic topic2 = new Topic();
        topic2.setTopicName("Lombok in Java");
        topic2.setUser(user);

        user.setTopics(List.of(topic, topic2)); //Damit wird ein User mit zwei Topics gespeichert.

        userRepository.save(user);
    }

    // Method to assign a user to a topic
    public void assignUserToTopic(Long userId, Long topicId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Topic> topicOpt = topicRepository.findById(topicId);

        if (userOpt.isPresent() && topicOpt.isPresent()) {
            User user = userOpt.get();
            Topic topic = topicOpt.get();

            // Prüfen, ob der User bereits in dem Topic ist (um doppelte Einträge zu vermeiden)
            if (!userTopicRepository.existsByUserAndTopic(user, topic)) {
                UserTopic userTopic = new UserTopic();
                userTopic.setUser(user);
                userTopic.setTopic(topic);
                userTopic.setJoinedAt(LocalDateTime.now());

                userTopicRepository.save(userTopic);
            }
        }
    }

}
