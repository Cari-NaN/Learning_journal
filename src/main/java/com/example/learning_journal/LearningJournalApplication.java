package com.example.learning_journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LearningJournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningJournalApplication.class, args);
		//Хеширование пароля
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String rawPassword = "admin123"; // Исходный пароль
//		String hashedPassword = encoder.encode(rawPassword); // Хешируем пароль
//		System.out.println("Захешированный пароль: " + hashedPassword);
	}
}
