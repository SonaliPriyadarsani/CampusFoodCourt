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

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Thymeleaf template
    }

   
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 🔐 encrypt
        user.setRole(Role.STUDENT);
        userService.saveUser(user);
        return "redirect:/login";
    }

}
