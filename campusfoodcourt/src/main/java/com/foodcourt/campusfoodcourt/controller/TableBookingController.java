package com.foodcourt.campusfoodcourt.controller;

import com.foodcourt.campusfoodcourt.entity.TableBooking;
import com.foodcourt.campusfoodcourt.entity.User;
import com.foodcourt.campusfoodcourt.repository.TableBookingRepository;
import com.foodcourt.campusfoodcourt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TableBookingController {

    @Autowired
    private TableBookingRepository tableBookingRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/student/book-table")
    public String showBookingForm(Model model) {
        model.addAttribute("success", false);  // by default
        return "student_book_table";
    }

    @PostMapping("/student/book-table")
    public String bookTable(@RequestParam int tableNumber, Authentication authentication, Model model) {
        String email = authentication.getName();
        User student = userService.getUserByEmail(email);

        TableBooking booking = new TableBooking();
        booking.setTableNumber(tableNumber);
        booking.setStudent(student);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("Booked");

        tableBookingRepository.save(booking);

        model.addAttribute("success", true);
        return "student_book_table";
    }

    @GetMapping("/student/bookings")
    public String viewBookings(Authentication authentication, Model model) {
        String email = authentication.getName();
        User student = userService.getUserByEmail(email);

        List<TableBooking> bookings = tableBookingRepository.findByStudent(student);
        model.addAttribute("bookings", bookings);

        return "student_bookings";
    }
}
