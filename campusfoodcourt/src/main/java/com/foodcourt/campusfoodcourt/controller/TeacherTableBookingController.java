package com.foodcourt.campusfoodcourt.controller;

import com.foodcourt.campusfoodcourt.entity.TeacherTableBooking;
import com.foodcourt.campusfoodcourt.entity.User;
import com.foodcourt.campusfoodcourt.repository.TeacherTableBookingRepository;
import com.foodcourt.campusfoodcourt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class TeacherTableBookingController {

    @Autowired
    private TeacherTableBookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/teacher/book-table")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new TeacherTableBooking());
        return "teacher_book_table";  // Create this HTML
    }

    @PostMapping("/teacher/book-table")
    public String bookTable(@ModelAttribute TeacherTableBooking booking,
                            Authentication authentication,
                            Model model) {
        String email = authentication.getName();
        User teacher = userService.getUserByEmail(email);

        booking.setTeacher(teacher);
        booking.setBookingTime(LocalDateTime.now());

        bookingRepository.save(booking);

        model.addAttribute("message", "Table booked successfully!");
        return "teacher_book_table";
    }
}
