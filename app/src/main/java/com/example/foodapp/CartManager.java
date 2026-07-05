package com.example.foodapp;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product) {
        cartItems.add(product);
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public void removeFromCart(Product product) {
        cartItems.remove(product);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : cartItems) {
            String priceStr = product.getPrice().replace("₹", "").trim().replace("/-", "");
            try {
                total += Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                // Ignore if price is not a number
            }
        }
        return total;
    }
}
