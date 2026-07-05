package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView rvWishlist;
    private ProductAdapter adapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.wishlist_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("My Wishlist");
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(WishlistActivity.this, AccessoriesList.class));
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(WishlistActivity.this, SearchActivity.class));
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(WishlistActivity.this, CartActivity.class));
            } else if (id == R.id.nav_history) {
                startActivity(new Intent(WishlistActivity.this, HistoryActivity.class));
            } else if (id == R.id.nav_wishlist) {
                drawerLayout.closeDrawers();
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(WishlistActivity.this, ProfileActivity.class));
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(WishlistActivity.this, SettingsActivity.class));
            } else if (id == R.id.nav_logout) {
                performLogout();
            }

            drawerLayout.closeDrawers();
            return true;
        });

        rvWishlist = findViewById(R.id.rv_wishlist);
        rvWishlist.setLayoutManager(new GridLayoutManager(this, 1));
        
        loadWishlist();
    }

    private void loadWishlist() {
        List<Product> wishlist = WishlistManager.getInstance().getWishlistItems();
        if (wishlist.isEmpty()) {
            Toast.makeText(this, "Your wishlist is empty", Toast.LENGTH_SHORT).show();
        }
        adapter = new ProductAdapter(wishlist);
        rvWishlist.setAdapter(adapter);
    }

    private void performLogout() {
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(WishlistActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
