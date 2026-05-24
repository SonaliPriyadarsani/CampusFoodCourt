package com.foodcourt.campusfoodcourt.service;

import com.foodcourt.campusfoodcourt.entity.Order;
import com.foodcourt.campusfoodcourt.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order placeOrder(Order order);

    List<Order> getOrdersByUser(User user);

    List<Order> getAllOrders();

    Optional<Order> getOrderById(Long id);

    void deleteOrder(Long id);

	List<Order> findByUser(User user);
}
