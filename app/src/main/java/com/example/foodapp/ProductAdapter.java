package com.example.foodapp;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private final List<Product> mProducts;

    public ProductAdapter(List<Product> mProducts) {
        this.mProducts = mProducts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = mProducts.get(position);

        Context myContext = holder.itemView.getContext();
        int resid = product.getImageResource();
        if (resid == 0 && product.getPhoto() != null) {
            resid = myContext.getResources().getIdentifier(product.getPhoto(), "drawable", myContext.getPackageName());
        }

        if (resid != 0) {
            holder.acessoriesImage.setImageResource(resid);
        }
        holder.acessoriesName.setText(product.getName());
        holder.acessoriesPrice.setText(product.getPrice());
        holder.acessoriesDescription.setText(product.getDescription());

        // Set wishlist icon color based on status
        updateWishlistIcon(holder.btnWishlist, WishlistManager.getInstance().isInWishlist(product));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(myContext, DisplayProduct.class);
            intent.putExtra("Click id", product.getId());
            intent.putExtra("Click name", product.getName());
            intent.putExtra("Click price", product.getPrice());
            intent.putExtra("Click description", product.getDescription());
            intent.putExtra("Click image", product.getImageResource());
            myContext.startActivity(intent);
        });

        holder.btnAddToCart.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(product);
            Toast.makeText(v.getContext(), product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        });

        holder.btnWishlist.setOnClickListener(v -> {
            WishlistManager wishlistManager = WishlistManager.getInstance();
            if (wishlistManager.isInWishlist(product)) {
                wishlistManager.removeFromWishlist(product);
                updateWishlistIcon(holder.btnWishlist, false);
                Toast.makeText(myContext, "Removed from wishlist", Toast.LENGTH_SHORT).show();
            } else {
                wishlistManager.addToWishlist(product);
                updateWishlistIcon(holder.btnWishlist, true);
                Toast.makeText(myContext, "Added to wishlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateWishlistIcon(ImageButton btn, boolean isInWishlist) {
        if (isInWishlist) {
            ImageViewCompat.setImageTintList(btn, ColorStateList.valueOf(Color.RED));
        } else {
            ImageViewCompat.setImageTintList(btn, ColorStateList.valueOf(Color.GRAY));
        }
    }

    @Override
    public int getItemCount() {
        return mProducts != null ? mProducts.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView acessoriesPrice;
        TextView acessoriesName;
        TextView acessoriesDescription;
        ImageView acessoriesImage;
        Button btnAddToCart;
        ImageButton btnWishlist;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.row_item, parent, false));

            acessoriesPrice = itemView.findViewById(R.id.productPrice);
            acessoriesName = itemView.findViewById(R.id.prodctTitle);
            acessoriesDescription = itemView.findViewById(R.id.productDescription);
            acessoriesImage = itemView.findViewById(R.id.productImage);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            btnWishlist = itemView.findViewById(R.id.btnWishlist);
        }
    }
}
