package com.example.foodapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

public class DisplayProduct extends AppCompatActivity {

    private TextView priceDisplayText, nameDisplayText, tvQuantity;
    private ImageView accessoriesDisplayImage;
    private Button buyButton, addToCartButton;
    private ImageButton btnDisplayWishlist, btnPlus, btnMinus;
    private RatingBar productRating;
    
    private int quantity = 1;
    private double basePrice = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_product);

        // Initialize views
        priceDisplayText = findViewById(R.id.displayPrice);
        nameDisplayText = findViewById(R.id.displayName);
        TextView accessoriesDescription = findViewById(R.id.displayDescription);
        accessoriesDisplayImage = findViewById(R.id.displayImage);
        buyButton = findViewById(R.id.buyButton);
        addToCartButton = findViewById(R.id.addToCartButton);
        btnDisplayWishlist = findViewById(R.id.btnDisplayWishlist);
        
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        tvQuantity = findViewById(R.id.tvQuantity);
        productRating = findViewById(R.id.productRating);

        // Get data from intent
        Intent i = getIntent();
        int productId = i.getIntExtra("Click id", -1);
        String price = i.getStringExtra("Click price");
        String name = i.getStringExtra("Click name");
        String description = i.getStringExtra("Click description");
        int imageResource = i.getIntExtra("Click image", 0);

        Product currentProduct = AccessoriesSingleton.getInstance().getProduct(productId);

        // Set data to views
        if (name != null) nameDisplayText.setText(name);
        if (price != null) {
            priceDisplayText.setText(price);
            try {
                String cleanPrice = price.replaceAll("[^0-9]", "");
                if (!cleanPrice.isEmpty()) {
                    basePrice = Double.parseDouble(cleanPrice);
                }
            } catch (Exception e) {
                basePrice = 0;
            }
        }
        
        updateTotalPrice();
        if (description != null) accessoriesDescription.setText(description);
        if (imageResource != 0) accessoriesDisplayImage.setImageResource(imageResource);

        if (currentProduct != null) {
            updateWishlistIcon(WishlistManager.getInstance().isInWishlist(currentProduct));
        }

        // Quantity Logic
        btnPlus.setOnClickListener(v -> {
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
            updateTotalPrice();
        });

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        // Rating logic
        productRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                Toast.makeText(this, "Thank you for rating: " + rating, Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listeners
        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(DisplayProduct.this, Checkout.class);
            startActivity(intent);
        });

        addToCartButton.setOnClickListener(v -> {
            if (currentProduct != null) {
                for (int j = 0; j < quantity; j++) {
                    CartManager.getInstance().addToCart(currentProduct);
                }
                Toast.makeText(DisplayProduct.this, quantity + " x " + name + " added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DisplayProduct.this, "Error: Item not found", Toast.LENGTH_SHORT).show();
            }
        });

        btnDisplayWishlist.setOnClickListener(v -> {
            if (currentProduct != null) {
                WishlistManager wishlistManager = WishlistManager.getInstance();
                if (wishlistManager.isInWishlist(currentProduct)) {
                    wishlistManager.removeFromWishlist(currentProduct);
                    updateWishlistIcon(false);
                    Toast.makeText(this, "Removed from wishlist", Toast.LENGTH_SHORT).show();
                } else {
                    wishlistManager.addToWishlist(currentProduct);
                    updateWishlistIcon(true);
                    Toast.makeText(this, "Added to wishlist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateTotalPrice() {
        int total = (int) (quantity * basePrice);
        String name = nameDisplayText.getText().toString();
        priceDisplayText.setText(getString(R.string.total_price_format, name, quantity, total));
    }

    private void updateWishlistIcon(boolean isInWishlist) {
        if (isInWishlist) {
            ImageViewCompat.setImageTintList(btnDisplayWishlist, ColorStateList.valueOf(Color.RED));
        } else {
            ImageViewCompat.setImageTintList(btnDisplayWishlist, ColorStateList.valueOf(Color.GRAY));
        }
    }
}
