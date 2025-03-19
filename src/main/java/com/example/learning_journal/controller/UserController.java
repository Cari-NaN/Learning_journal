package com.example.learning_journal.controller;

import com.example.learning_journal.model.LearningClass;
import com.example.learning_journal.model.User;
import com.example.learning_journal.service.LearningClassService;
import com.example.learning_journal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LearningClassService learningClassService;

    // Показать форму регистрации
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Обработать регистрацию
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {
        try {
            userService.registerUser(firstName, lastName, username, email, password, confirmPassword);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // Страница со списком пользователей (для админов)
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "manage-users";  // Теперь это /admin/manage-users
    }

    // Форма редактирования пользователя
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit";  // Открывает user-edit.html
    }

    // Обновление данных пользователя
    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute User updatedUser) {
        userService.updateUser(id, updatedUser);
        System.out.println("Редактирование пользователя " + id + " выполнено. Перенаправляем на /admin/manage-users");
        return "redirect:/admin/manage-users";
    }

    // Удаление пользователя (только для админов)
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/manage-users";  // Возвращает в список пользователей
    }

    // Форма настроек пользователя
    @GetMapping("/settings")
    public String showSettingsPage(Model model) {
        model.addAttribute("user", new User());
        return "user-settings";
    }

    // Добавляем GET-запрос для отображения страницы смены пароля
    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "change-password"; // Загружает шаблон change-password.html
    }

    // Обработка запроса на изменение пароля
    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model,
            Principal principal) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "❌ Neues Passwort und Bestätigung stimmen nicht überein!");
            return "change-password"; // Остаемся на странице смены пароля
        }

        try {
            userService.changePassword(principal.getName(), oldPassword, newPassword, confirmPassword);
            model.addAttribute("success", "✅ Passwort erfolgreich geändert!");
            return "redirect:/login"; // После успешного изменения — переадресация на логин
        } catch (Exception e) {
            model.addAttribute("error", "❌ " + e.getMessage());
            return "change-password"; // Остаемся на странице, если есть ошибка
        }
    }

    // GET-запрос для отображения страницы удаления аккаунта
    @GetMapping("/delete-account")
    public String showDeleteAccountForm() {
        return "delete-account"; // Загружаем HTML-шаблон delete-account.html
    }

    // Обработка запроса на удаление аккаунта
    @PostMapping("/delete-account")
    public String deleteAccount(Principal principal, RedirectAttributes redirectAttributes) {
        userService.deleteByUsername(principal.getName());
        redirectAttributes.addFlashAttribute("info", "Konto erfolgreich gelöscht!");

        return "redirect:/";
    }

    // Отображение текущих классов пользователя
    @GetMapping("/my-classes")
    public String showUserClasses(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<LearningClass> userClasses = new ArrayList<>(user.getClasses()); // Преобразуем Set в List
        List<LearningClass> allClasses = learningClassService.getAllClasses();

        model.addAttribute("userClasses", userClasses);
        model.addAttribute("allClasses", allClasses);

        return "my-classes";
    }

    // Присоединение к классу
    @PostMapping("/join-class")
    public String joinClass(@RequestParam Long classId, Principal principal) {
        userService.addUserToClass(principal.getName(), classId);
        return "redirect:/users/my-classes"; // Перенаправление обратно на страницу
    }

    // Выход из класса
    @PostMapping("/leave-class")
    public String leaveClass(@RequestParam Long classId, Principal principal) {
        userService.removeUserFromClass(principal.getName(), classId);
        return "redirect:/users/my-classes"; // Перенаправление обратно на страницу
    }


    // Показать участников класса
    @GetMapping("/class-members")
    public String showClassMembers(@RequestParam Long classId, Model model, Principal principal) {
        List<User> members = learningClassService.getStudentsInClass(classId);
        LearningClass selectedClass = learningClassService.getClassById(classId); // Получаем класс по ID

        model.addAttribute("classMembers", members);
        model.addAttribute("selectedClass", selectedClass); // Передаем в модель

        return showUserClasses(model, principal);
    }
}




