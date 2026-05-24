package com.foodcourt.campusfoodcourt.service;

import com.foodcourt.campusfoodcourt.entity.CartItem;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final List<CartItem> cartItems = new ArrayList<>();

    // Add new item
    public void addItem(CartItem item) {
        cartItems.add(item);
    }

    // Get all items
    public List<CartItem> getAllItems() {
        return cartItems;
    }

    // Update quantity
    public void updateQuantity(Long menuItemId, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getMenuItem().equals(menuItemId)) {
                item.setQuantity(quantity);
                item.setTotal(item.getPrice() * quantity);
                break;
            }
        }
    }

    // Remove one item
    public void removeItem(Long menuItemId) {
        cartItems.removeIf(item -> item.getMenuItem().equals(menuItemId));
    }

    // Optional — clear entire cart
    public void clearCart() {
        cartItems.clear();
    }
}
