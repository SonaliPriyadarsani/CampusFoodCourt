package com.foodcourt.campusfoodcourt.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders") 

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the User who placed the order
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ID of the menu item being ordered
    private Long menuItemId;

    // Quantity ordered
    private int quantity;

    // Total price for this order
    private double totalPrice;

    // Optional: Order time
    private LocalDateTime orderTime = LocalDateTime.now();

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

	public void setStatus(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setMenuItem(MenuItem menuItem) {
		// TODO Auto-generated method stub
		
	}
}
