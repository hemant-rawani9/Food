package com.example.foodapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WishlistManager {
    private static WishlistManager instance;
    private final List<Product> wishlistItems;

    private WishlistManager() {
        wishlistItems = new ArrayList<>();
    }

    public static synchronized WishlistManager getInstance() {
        if (instance == null) {
            instance = new WishlistManager();
        }
        return instance;
    }

    public void addToWishlist(Product product) {
        if (!isInWishlist(product)) {
            wishlistItems.add(product);
        }
    }

    public List<Product> getWishlistItems() {
        return wishlistItems;
    }

    public void removeFromWishlist(Product product) {
        Iterator<Product> iterator = wishlistItems.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == product.getId()) {
                iterator.remove();
                break;
            }
        }
    }

    public boolean isInWishlist(Product product) {
        for (Product item : wishlistItems) {
            if (item.getId() == product.getId()) {
                return true;
            }
        }
        return false;
    }
}
