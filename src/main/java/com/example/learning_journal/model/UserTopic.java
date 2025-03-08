package com.example.learning_journal.model;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

//UserTopic-Klasse (Zwischentabelle als Entity)
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    private LocalDateTime joinedAt;
}
