package com.foodcourt.campusfoodcourt.controller;



import org.springframework.security.access.prepost.PreAuthorize;
import com.foodcourt.campusfoodcourt.repository.MenuItemRepository;
import com.foodcourt.campusfoodcourt.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.foodcourt.campusfoodcourt.entity.MenuItem;
import com.foodcourt.campusfoodcourt.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminController {

	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;


    @GetMapping("/admin/menu")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) 
    {
        return "admin_dashboard";  
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
    
    @PostMapping("/admin/add-menu-item")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveMenuItem(@ModelAttribute MenuItem menuItem) {
        menuItemRepository.save(menuItem);
        return "redirect:/admin/menulist?added=true";
    }

    @GetMapping("/admin/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        
        LocalDateTime now = LocalDateTime.now();
        for (Order order : orders) {
            if (order.getOrderTime() != null &&
                order.getOrderTime().plusMinutes(30).isBefore(now)) {
                order.setStatus("Completed");
            } else {
                order.setStatus("Pending");
            }
        }

        model.addAttribute("orders", orders);
        return "admin_view_orders";
    }

}