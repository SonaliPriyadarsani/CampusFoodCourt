package com.foodcourt.campusfoodcourt.repository;
import com.foodcourt.campusfoodcourt.entity.Order;
import com.foodcourt.campusfoodcourt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

	List<Order> findByUserAndOrderTimeBetween(User user, LocalDateTime atStartOfDay, LocalDateTime atTime);
}
