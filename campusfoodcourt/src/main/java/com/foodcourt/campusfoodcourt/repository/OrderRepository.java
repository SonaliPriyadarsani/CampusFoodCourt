package com.foodcourt.campusfoodcourt.repository;
import com.foodcourt.campusfoodcourt.entity.Order;
import com.foodcourt.campusfoodcourt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
