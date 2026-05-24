package com.foodcourt.campusfoodcourt.controller;

import com.foodcourt.campusfoodcourt.entity.Role;
import com.foodcourt.campusfoodcourt.entity.User;
import com.foodcourt.campusfoodcourt.service.UserService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        boolean adminExists = userService.adminExists();
        model.addAttribute("adminExists", adminExists);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == Role.ADMIN && userService.adminExists()) {
            return "redirect:/register?error=admin_exists";
        }

        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        User user = userService.getLoggedInUser(principal);

        if (user == null) {
            return "redirect:/login";
        }

        // Send role to Thymeleaf
        model.addAttribute("role", user.getRole().name()); // e.g. STUDENT or TEACHER
        return "menu_home";
    }
    
    @GetMapping("/menu_home")
    public String menuHome(Model model, Principal principal) {
        User user = userService.getLoggedInUser(principal);

        if (user != null) {
            model.addAttribute("role", user.getRole());
        }
        model.addAttribute("role", user.getRole().name()); // e.g. STUDENT or TEACHER
        return "menu_home"; // this must match menu_home.html under templates/
    }
}
