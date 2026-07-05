package com.example.foodapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;


public class AccessoriesList extends AppCompatActivity {


    RecyclerView rView;
    ProductAdapter adapter;
    
    AccessoriesSingleton pSingleton;
    List<Product> myProductList= new ArrayList<>();

    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acessories_list);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rView = findViewById(R.id.Rview);
        pSingleton = AccessoriesSingleton.getInstance();
        
        String category = getIntent().getStringExtra("CATEGORY");
        if (category != null && !category.isEmpty()) {
            myProductList.addAll(pSingleton.getProductsByCategory(category));
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(category);
            }
        } else {
            myProductList.addAll(pSingleton.getProductList());
        }

        adapter = new ProductAdapter(myProductList);
        rView.setLayoutManager(new GridLayoutManager(this, 1));
        rView.setAdapter(adapter);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(AccessoriesList.this, DashboardActivity.class));
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(AccessoriesList.this, SearchActivity.class));
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(AccessoriesList.this, CartActivity.class));
            } else if (id == R.id.nav_history) {
                startActivity(new Intent(AccessoriesList.this, HistoryActivity.class));
            } else if (id == R.id.nav_wishlist) {
                startActivity(new Intent(AccessoriesList.this, WishlistActivity.class));
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(AccessoriesList.this, ProfileActivity.class));
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(AccessoriesList.this, SettingsActivity.class));
            } else if (id == R.id.nav_logout) {
                performLogout();
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_search) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        } else if (id == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        } else if (id == R.id.action_logout) {
            performLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performLogout() {
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AccessoriesList.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}