package com.foodcourt.campusfoodcourt.controller;

import com.foodcourt.campusfoodcourt.entity.Order;
import com.foodcourt.campusfoodcourt.entity.User;
import com.foodcourt.campusfoodcourt.repository.OrderRepository;
import com.foodcourt.campusfoodcourt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/booking")
public class TableBookingController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    private static int bookedTableNumber = -1;
    private static LocalDateTime bookingTime = null;
    private static String bookingRole = null;

    // ✅ STUDENT: GET page
    @GetMapping("/student")
    public String showStudentBooking(Model model, Principal principal) {
        clearExpiredBooking();
        User user = userService.getUserByEmail(principal.getName());

        if ("STUDENT".equalsIgnoreCase(user.getRole().name()) &&
                bookedTableNumber != -1 &&
                "STUDENT".equalsIgnoreCase(bookingRole)) {
            model.addAttribute("tableNumber", bookedTableNumber);
        }

        return "student_book_table";
    }

    // ✅ STUDENT: POST booking
    @PostMapping("/student")
    public String bookStudentTable(@RequestParam int members, Model model, Principal principal) {
        clearExpiredBooking();
        User user = userService.getUserByEmail(principal.getName());

        bookedTableNumber = new Random().nextInt(30) + 1; // 1–30
        bookingTime = LocalDateTime.now();
        bookingRole = "STUDENT";

        model.addAttribute("tableNumber", bookedTableNumber);
        return "student_book_table";
    }

    // ✅ TEACHER: GET page
    @GetMapping("/teacher")
    public String showTeacherBooking(Model model, Principal principal) {
        clearExpiredBooking();
        User user = userService.getUserByEmail(principal.getName());

        if ("TEACHER".equalsIgnoreCase(user.getRole().name()) &&
                bookedTableNumber != -1 &&
                "TEACHER".equalsIgnoreCase(bookingRole)) {
            model.addAttribute("tableNumber", bookedTableNumber);
        }

        return "teacher_book_table";
    }

    // ✅ TEACHER: POST booking
    @PostMapping("/teacher")
    public String handleTeacherBooking(@RequestParam String foodAtDesk,
                                       @RequestParam(required = false) Integer members,
                                       Principal principal,
                                       Model model) {
        clearExpiredBooking();
        User user = userService.getUserByEmail(principal.getName());

        List<Order> orders = orderRepository.findByUser(user);
        boolean hasRecentOrder = orders.stream().anyMatch(order ->
                order.getOrderTime() != null &&
                        order.getOrderTime().isAfter(LocalDateTime.now().minusMinutes(30))
        );

        if ("yes".equalsIgnoreCase(foodAtDesk)) {
            if (hasRecentOrder) {
                model.addAttribute("message", "✅ We will meet you soon at your desk!");
            } else {
                model.addAttribute("error", "⚠️ You did not order anything yet. Please order first.");
            }
            return "teacher_book_table";
        }

        if (members != null && members > 0) {
            bookedTableNumber = new Random().nextInt(21) + 40; // 40–60
            bookingTime = LocalDateTime.now();
            bookingRole = "TEACHER";
            model.addAttribute("tableNumber", bookedTableNumber);
        }

        return "teacher_book_table";
    }

    // ✅ Helper to clear expired booking
    private void clearExpiredBooking() {
        if (bookingTime != null && bookingTime.plusMinutes(30).isBefore(LocalDateTime.now())) {
            bookedTableNumber = -1;
            bookingTime = null;
            bookingRole = null;
        }
    }
}
