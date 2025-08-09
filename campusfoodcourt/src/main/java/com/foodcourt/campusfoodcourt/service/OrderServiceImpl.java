package com.foodcourt.campusfoodcourt.service;

import com.foodcourt.campusfoodcourt.entity.MenuItem;
import com.foodcourt.campusfoodcourt.entity.Order;
import com.foodcourt.campusfoodcourt.entity.User;
import com.foodcourt.campusfoodcourt.repository.MenuItemRepository;
import com.foodcourt.campusfoodcourt.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository; // <-- ✅ Add this line here

    @Override
    public Order placeOrder(Order order) {
        // ✅ Get the MenuItem using menuItemId from the order
        MenuItem menuItem = menuItemRepository.findById(order.getMenuItem().getId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        // ✅ Calculate total price
        double totalPrice = menuItem.getPrice() * order.getQuantity();
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
