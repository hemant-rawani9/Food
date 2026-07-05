package com.example.foodapp;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

public class HistoryActivity extends AppCompatActivity {

    private HistoryAdapter adapter;
    private DatabaseHelper dbHelper;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.history_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Order History");
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(HistoryActivity.this, AccessoriesList.class));
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(HistoryActivity.this, SearchActivity.class));
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(HistoryActivity.this, CartActivity.class));
            } else if (id == R.id.nav_history) {
                // Already here
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(HistoryActivity.this, ProfileActivity.class));
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(HistoryActivity.this, SettingsActivity.class));
            } else if (id == R.id.nav_logout) {
                performLogout();
            }

            drawerLayout.closeDrawers();
            return true;
        });

        dbHelper = new DatabaseHelper(this);
        RecyclerView rvHistory = findViewById(R.id.rv_history);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HistoryAdapter(null);
        rvHistory.setAdapter(adapter);

        loadOrderHistory();
    }

    private void loadOrderHistory() {
        Cursor cursor = dbHelper.getAllOrders();
        adapter.swapCursor(cursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.action_clear_history) {
            showClearHistoryDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showClearHistoryDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Clear History")
                .setMessage("Are you sure you want to delete all order history?")
                .setPositiveButton("Yes, Clear", (dialog, which) -> {
                    dbHelper.clearAllOrders();
                    loadOrderHistory(); // Refresh the list
                    Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void performLogout() {
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HistoryActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (drawerLayout.isDrawerOpen(androidx.core.view.GravityCompat.START)) {
            drawerLayout.closeDrawer(androidx.core.view.GravityCompat.START);
        } else {
            finish();
        }
        return true;
    }
}
