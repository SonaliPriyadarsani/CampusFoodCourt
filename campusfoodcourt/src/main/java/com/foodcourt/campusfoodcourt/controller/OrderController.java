package com.foodcourt.campusfoodcourt.controller;

import com.foodcourt.campusfoodcourt.entity.Order;
import com.foodcourt.campusfoodcourt.entity.User;
import com.foodcourt.campusfoodcourt.repository.MenuItemRepository;
import com.foodcourt.campusfoodcourt.repository.OrderRepository;
import com.foodcourt.campusfoodcourt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserService userService;

    
    @GetMapping("/student/order")
    public String showOrderPage(Model model) {
        model.addAttribute("menuItems", menuItemRepository.findAll());
        model.addAttribute("orderSuccess", false);  // default
        return "studentorder";
    }

    @GetMapping("/student_bookings")
    public String viewBookings(Model model, Principal principal) {
        User user = userService.getLoggedInUser(principal);
        List<Order> orders = orderRepository.findByUser(user);
        model.addAttribute("orders", orders);
        return "student_bookings";
    }
    
    @GetMapping("/student/orders")
    public String viewStudentOrders(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        model.addAttribute("orders", orderRepository.findByUser(user));
        return "student_orders";  // Create this HTML
    }
    
    @GetMapping("/orders")
    public String viewUserOrders(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        model.addAttribute("orders", orderRepository.findByUser(user));
        return "user_orders"; // Create this page
    }

}
