package com.foodcourt.campusfoodcourt.repository;

import com.foodcourt.campusfoodcourt.entity.TeacherTableBooking;
import com.foodcourt.campusfoodcourt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherTableBookingRepository extends JpaRepository<TeacherTableBooking, Long> {
    List<TeacherTableBooking> findByTeacher(User teacher);
}
