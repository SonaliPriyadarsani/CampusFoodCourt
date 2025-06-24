package com.foodcourt.campusfoodcourt.repository;


import com.foodcourt.campusfoodcourt.entity.TableBooking;
import com.foodcourt.campusfoodcourt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableBookingRepository extends JpaRepository<TableBooking, Long> {
    List<TableBooking> findByUser(User user);
}

