package com.foodcourt.campusfoodcourt.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TableBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user; // only students can book

    private LocalDateTime bookingTime;

    // Getters and Setters
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

}
