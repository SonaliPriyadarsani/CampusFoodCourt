package com.foodcourt.campusfoodcourt.controller;


import com.foodcourt.campusfoodcourt.entity.TableBooking;
import com.foodcourt.campusfoodcourt.service.TableBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/table")
public class TableBookingController {

    @Autowired
    private TableBookingService bookingService;

    @PostMapping("/book")
    public String bookTable(@ModelAttribute TableBooking booking) {
        booking.setBookingTime(LocalDateTime.now());
        bookingService.bookTable(booking);
        return "redirect:/menu";
    }
}
