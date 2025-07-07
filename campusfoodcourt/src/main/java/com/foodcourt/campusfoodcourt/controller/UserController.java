package com.foodcourt.campusfoodcourt.controller;

import com.foodcourt.campusfoodcourt.entity.Role;
import com.foodcourt.campusfoodcourt.entity.User;
import com.foodcourt.campusfoodcourt.service.UserService;
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
}
