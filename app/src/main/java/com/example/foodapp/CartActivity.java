package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartItemChangeListener {

    private RecyclerView recyclerView;
    private TextView totalAmountText;
    private Button btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartRecyclerView);
        totalAmountText = findViewById(R.id.totalAmountText);
        btnProceed = findViewById(R.id.btnProceedToCheckout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CartAdapter adapter = new CartAdapter(CartManager.getInstance().getCartItems(), this);
        recyclerView.setAdapter(adapter);

        updateTotal();

        btnProceed.setOnClickListener(v -> {
            if (CartManager.getInstance().getCartItems().isEmpty()) {
                Toast.makeText(CartActivity.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(CartActivity.this, Checkout.class);
                startActivity(intent);
            }
        });
    }

    private void updateTotal() {
        totalAmountText.setText("₹ " + CartManager.getInstance().getTotalPrice());
    }

    @Override
    public void onCartChanged() {
        updateTotal();
    }
}
