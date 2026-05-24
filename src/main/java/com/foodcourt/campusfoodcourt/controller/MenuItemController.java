package com.foodcourt.campusfoodcourt.controller;

import com.foodcourt.campusfoodcourt.entity.MenuItem;
import com.foodcourt.campusfoodcourt.entity.User;

import org.springframework.security.core.Authentication;
import com.foodcourt.campusfoodcourt.repository.MenuItemRepository;
import com.foodcourt.campusfoodcourt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MenuItemController {

    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private UserService userService;


    // Accessible to all users (Students, Teachers)
    @GetMapping("/menu")
    public String showMenu(Model model, Authentication authentication) {
        List<MenuItem> items = menuItemRepository.findAll();
        model.addAttribute("menuItems", items);

        // ✅ Fix for Thymeleaf condition
        if (!model.containsAttribute("orderSuccess")) {
            model.addAttribute("orderSuccess", null);
        }

        // ✅ Add user role
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();  // Logged-in user email
            User user = userService.getUserByEmail(email);  // Your custom User
            model.addAttribute("role", user.getRole().name());  // Example: STUDENT, TEACHER, ADMIN
        }

        return "menu";
    }


    // Admin-only: form to add menu item
    @GetMapping("/admin/addmenuitem")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddMenuItemForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "addmenuitem";  // Create addmenuitem.html
    }

    // Admin-only: handle form submission
    @PostMapping("/admin/addmenuitem")
    @PreAuthorize("hasRole('ADMIN')")
    public String addMenuItem(@ModelAttribute MenuItem menuItem) {
        menuItemRepository.save(menuItem);
        return "redirect:/admin/menu";  // Redirect to admin menu list
    }
}
