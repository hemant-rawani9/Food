package com.example.foodapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<Product> cartItems;
    private final OnCartItemChangeListener listener;

    public interface OnCartItemChangeListener {
        void onCartChanged();
    }

    public CartAdapter(List<Product> cartItems, OnCartItemChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());
        holder.image.setImageResource(product.getImageResource());

        holder.removeBtn.setOnClickListener(v -> {
            int currentPosition = holder.getBindingAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                CartManager.getInstance().removeFromCart(product);
                notifyItemRemoved(currentPosition);
                if (listener != null) {
                    listener.onCartChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView image, removeBtn;
        TextView name, price;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cartItemImage);
            name = itemView.findViewById(R.id.cartItemName);
            price = itemView.findViewById(R.id.cartItemPrice);
            removeBtn = itemView.findViewById(R.id.btnRemoveItem);
        }
    }
}
