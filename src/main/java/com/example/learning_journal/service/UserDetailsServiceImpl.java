package com.example.learning_journal.service;

import com.example.learning_journal.model.User;
import com.example.learning_journal.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional.map(user ->
                org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                        .password(user.getPassword()) // Пароль уже хеширован в БД
                        .roles(user.getRole().name()) // Преобразуем enum Role в строку
                        .build()
        ).orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden: " + username));
    }
}



