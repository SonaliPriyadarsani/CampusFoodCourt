package com.foodcourt.campusfoodcourt.controller;



import org.springframework.security.access.prepost.PreAuthorize;
import com.foodcourt.campusfoodcourt.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.foodcourt.campusfoodcourt.entity.MenuItem;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminController {

	@Autowired
	private MenuItemRepository menuItemRepository;

    @GetMapping("/admin/menu")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) {
        // You can pass menu stats, today's orders etc. here later
        return "admin_dashboard";  // Create this HTML
    }
    
    @GetMapping("/admin/menulist")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminMenuList(Model model) {
        List<MenuItem> items = menuItemRepository.findAll();  // Inject this repository
        model.addAttribute("menuItems", items);
        return "admin_menu_list";  // Make sure this HTML exists
    }
    
    @PostMapping("/admin/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        menuItemRepository.deleteById(id);
        return "redirect:/admin/menu?deleted=true";
    }

}