package com.foodcourt.campusfoodcourt.controller;

import com.foodcourt.campusfoodcourt.entity.MenuItem;
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

import java.time.LocalDateTime;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/order/place")
    public String placeOrder(@RequestParam Long menuItemId,
                             @RequestParam int quantity,
                             Authentication authentication,
                             Model model) {

        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElse(null);
        if (menuItem == null || !menuItem.isAvailable()) {
            model.addAttribute("orderSuccess", false);
            model.addAttribute("menuItems", menuItemRepository.findAll());
            return "menu";
        }

        String userEmail = authentication.getName();
        User user = userService.getUserByEmail(userEmail);

        Order order = new Order();
        order.setMenuItem(menuItem);
        order.setQuantity(quantity);
        order.setUser(user);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("Ordered");

        orderRepository.save(order);

        model.addAttribute("orderSuccess", true);
        model.addAttribute("menuItems", menuItemRepository.findAll());

        return "menu";
    }
}
