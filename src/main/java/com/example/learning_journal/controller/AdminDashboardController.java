package com.example.learning_journal.controller;

import com.example.learning_journal.service.UserService;
import com.example.learning_journal.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    private final UserService userService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        return "admin-dashboard";
    }

    @GetMapping("/admin/manage-users")
    public String manageUsers(Model model) {
        // Получаем всех пользователей
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "manage-users";  // Возвращает страницу manage-users.html
    }
}

