package com.foodcourt.campusfoodcourt.controller;

import com.foodcourt.campusfoodcourt.entity.MenuItem;
import com.foodcourt.campusfoodcourt.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;   // Add this import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;    // Add this import
import org.springframework.web.bind.annotation.PostMapping;    // Add this import

import java.util.List;

@Controller
public class MenuItemController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<MenuItem> items = menuItemRepository.findAll();
        model.addAttribute("menuItems", items);
        return "menu"; // Return menu.html from templates
    }

    // Admin-only add menu item form
    @GetMapping("/admin/addmenuitem")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddMenuItemForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "add_menuitem";  // Create this Thymeleaf template for the form
    }

    // Admin-only POST endpoint to add menu item
    @PostMapping("/admin/addmenuitem")
    @PreAuthorize("hasRole('ADMIN')")
    public String addMenuItem(@ModelAttribute MenuItem menuItem) {
        menuItemRepository.save(menuItem);
        return "redirect:/menu";  // Redirect to menu page after adding
    }
}
