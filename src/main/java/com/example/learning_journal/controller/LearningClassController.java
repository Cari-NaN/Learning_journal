package com.example.learning_journal.controller;

import com.example.learning_journal.model.LearningClass;
import com.example.learning_journal.model.User;
import com.example.learning_journal.service.LearningClassService;
import com.example.learning_journal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/classes") // Все маршруты начинаются с /admin/classes
public class LearningClassController {

    private final LearningClassService learningClassService;
    private final UserService userService;

    // Показать список всех классов
    @GetMapping
    public String listClasses(Model model) {
        model.addAttribute("classes", learningClassService.getAllClasses());
        return "class-list";
    }

    // Показать форму создания класса
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("learningClass", new LearningClass());
        return "class-form";
    }

    // Показать форму редактирования класса
    @GetMapping("/edit")
    public String showEditForm(@RequestParam Long id, Model model) {
        model.addAttribute("learningClass", learningClassService.getClassById(id));
        return "class-form";
    }

    // Сохранить класс
    @PostMapping("/save")
    public String saveClass(@ModelAttribute LearningClass learningClass) {
        learningClassService.saveOrUpdateClass(learningClass);
        return "redirect:/admin/classes";
    }

    // Удалить класс
    @GetMapping("/delete")
    public String deleteClass(@RequestParam Long id) {
        learningClassService.deleteClass(id);
        return "redirect:/admin/classes";
    }

    // Форма назначения ученика в класс
    @GetMapping("/assign")
    public String showAssignForm(@RequestParam(required = false) Long userId,
                                 @RequestParam(required = false) Long classId,
                                 Model model) {
        List<User> users = userService.getAllUsers();
        List<LearningClass> classes = learningClassService.getAllClasses();

        // Проверяем, существуют ли userId и classId в базе
        if (classId != null && classId > 0) {
            model.addAttribute("learningClass", learningClassService.getClassById(classId));
        }

        if (userId != null && userId > 0) {
            model.addAttribute("user", userService.getUserById(userId));
        }

        model.addAttribute("users", users);
        model.addAttribute("classes", classes);

        return "class-assign";
    }

    // Назначить ученика в класс
    @PostMapping("/assign")
    public String assignStudent(@RequestParam(required = false) Long classId,
                                @RequestParam(required = false) Long userId) {
        System.out.println("Получены параметры: classId=" + classId + ", userId=" + userId);
        // Проверяем, есть ли данные
        if (classId == null || userId == null || classId == 0 || userId == 0) {
            return "redirect:/admin/classes?error=missing_parameters";
        }
        learningClassService.assignStudentToClass(classId, userId);
        return "redirect:/admin/classes";
    }

    // Удалить ученика из класса
    @PostMapping("/remove-student")
    public String removeStudent(@RequestParam(required = false) Long classId,
                                @RequestParam(required = false) Long userId) {
        if (classId == null || userId == null || classId == 0 || userId == 0) {
            return "redirect:/admin/classes?error=missing_parameters";
        }
        learningClassService.removeStudentFromClass(classId, userId);
        return "redirect:/admin/classes";
    }
}
