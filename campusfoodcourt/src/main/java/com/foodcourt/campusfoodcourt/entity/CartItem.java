package com.foodcourt.campusfoodcourt.entity;

public class CartItem {
    private Long menuItem;
    private String name;
    private double price;
    private int quantity;

    // Getters and Setters
    public Long getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(Long menuItem) {
        this.menuItem = menuItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    

}

