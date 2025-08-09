package com.foodcourt.campusfoodcourt.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foodcourt.campusfoodcourt.entity.CartItem;
import com.foodcourt.campusfoodcourt.entity.MenuItem;
import com.foodcourt.campusfoodcourt.entity.Order;
import com.foodcourt.campusfoodcourt.entity.Role;
import com.foodcourt.campusfoodcourt.entity.User;
import com.foodcourt.campusfoodcourt.repository.MenuItemRepository;
import com.foodcourt.campusfoodcourt.repository.OrderRepository;
import com.foodcourt.campusfoodcourt.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addToCart(@RequestParam("menuItemId") Long menuItemId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        // Get the cart from session or create a new one
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Fetch the menu item from DB
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElse(null);
        if (menuItem == null) {
            redirectAttributes.addFlashAttribute("error", "Menu item not found");
            return "redirect:/menu";
        }

        // Check if item already in cart
        boolean found = false;
        for (CartItem item : cart) {
            if (item.getMenuItem().equals(menuItemId)) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            CartItem newItem = new CartItem();
            newItem.setMenuItem(menuItemId); // ✅ corrected method name
            newItem.setName(menuItem.getName());
            newItem.setPrice(menuItem.getPrice());
            newItem.setQuantity(quantity);
            cart.add(newItem);
        }

        // Update session
        session.setAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("success", "Item added to cart");

        return "redirect:/menu";
    }


    @PostMapping("/place")
    public String placeFinalOrder(HttpSession session, Principal principal) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) return "redirect:/menu";

        User user = userService.getLoggedInUser(principal);
        if (user == null) {
            throw new RuntimeException("Logged in user not found");
        }

        for (CartItem c : cart) {
            // ✅ Fetch MenuItem by ID
            MenuItem menuItem = menuItemRepository.findById(c.getMenuItem())
                    .orElseThrow(() -> new RuntimeException("MenuItem not found"));

            Order order = new Order();
            order.setUser(user);
            order.setMenuItem(menuItem); // ✅ Set MenuItem object, not ID
            order.setQuantity(c.getQuantity());
            order.setTotalPrice(c.getQuantity() * c.getPrice());
            order.setOrderTime(LocalDateTime.now());

            orderRepository.save(order);
        }

        session.removeAttribute("cart");

        // ✅ Role-based redirection
        Role role = user.getRole(); // Assuming enum: STUDENT, TEACHER, ADMIN

        if ("STUDENT".equals(role.name())) {
            return "redirect:/student_orders";
        } else if ("TEACHER".equals(role.name())) {
            return "redirect:/teacher_bookings";
        } else {
            return "redirect:/admin_dashboard";
        }
    }

    
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long menuItem, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            // Remove item matching the given menuItem ID
            cart.removeIf(item -> item.getMenuItem().equals(menuItem));
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCartQuantity(@RequestParam Long menuItem,
                                     @RequestParam int quantity,
                                     HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getMenuItem().equals(menuItem)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @GetMapping("")
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        double total = cart.stream()
                           .mapToDouble(i -> i.getPrice() * i.getQuantity())
                           .sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }
    

    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable Long id, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getMenuItem().equals(id));
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

}
