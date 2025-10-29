package com.foodcourt.campusfoodcourt.controller;

import com.foodcourt.campusfoodcourt.entity.Order;
import com.foodcourt.campusfoodcourt.entity.User;
import com.foodcourt.campusfoodcourt.repository.OrderRepository;
import com.foodcourt.campusfoodcourt.service.OrderService;
import com.foodcourt.campusfoodcourt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;
    @Autowired private OrderService orderService;
    
          
    @GetMapping("/student/bookings")
    public String studentBookings(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());

        // Fetch all orders of this user
        List<Order> orders = orderService.findByUser(user);

        // Keep only orders placed within the last 30 minutes
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);
        List<Order> recentOrders = orders.stream()
                .filter(order -> order.getOrderTime().isAfter(thirtyMinutesAgo))
                .toList();
        model.addAttribute("userRole", user.getRole().name()); // ✅ Add this line

        model.addAttribute("orders", recentOrders);

        return "student_Bookings";
    }

    @GetMapping("/student_orders")
    public String viewStudentOrders(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        List<Order> orders = orderRepository.findByUser(user);

        // Auto-complete orders after 30 minutes
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
        model.addAttribute("userRole", user.getRole().name());  // ✅ Pass user role
        model.addAttribute("hasOrders", !orders.isEmpty()); // ✅ flag
        return "student_orders";
    }
    
 


}
