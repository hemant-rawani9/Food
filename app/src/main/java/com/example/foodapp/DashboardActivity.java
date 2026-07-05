package com.example.foodapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.dashboard_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Dashboard");
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                drawerLayout.closeDrawers();
            } else if (id == R.id.nav_search) {
                startActivity(new Intent(DashboardActivity.this, SearchActivity.class));
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(DashboardActivity.this, CartActivity.class));
            } else if (id == R.id.nav_history) {
                startActivity(new Intent(DashboardActivity.this, HistoryActivity.class));
            } else if (id == R.id.nav_wishlist) {
                startActivity(new Intent(DashboardActivity.this, WishlistActivity.class));
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
            } else if (id == R.id.nav_logout) {
                performLogout();
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // System bars padding
        View mainView = findViewById(R.id.drawer_layout);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Initialize UI elements
        EditText searchBar = findViewById(R.id.searchBar);
        Button btnPizza = findViewById(R.id.btnPizza);
        Button btnBurger = findViewById(R.id.btnBurger);
        Button btnChinese = findViewById(R.id.btnChinese);
        Button btnSouthIndian = findViewById(R.id.btnSouthIndian);
        Button btnDesserts = findViewById(R.id.btnDesserts);
        Button btnBeverages = findViewById(R.id.btnBeverages);

        // Make search bar non-focusable so it acts like a button
        if (searchBar != null) {
            searchBar.setFocusable(false);
            searchBar.setClickable(true);
            searchBar.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                startActivity(intent);
            });
        }

        // Category click listener
        View.OnClickListener categoryClickListener = v -> {
            Button b = (Button) v;
            String category = b.getText().toString();
            Toast.makeText(DashboardActivity.this, "Opening " + category, Toast.LENGTH_SHORT).show();
            
            Intent intent = new Intent(DashboardActivity.this, AccessoriesList.class);
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
        };

        if (btnPizza != null) btnPizza.setOnClickListener(categoryClickListener);
        if (btnBurger != null) btnBurger.setOnClickListener(categoryClickListener);
        if (btnChinese != null) btnChinese.setOnClickListener(categoryClickListener);
        if (btnSouthIndian != null) btnSouthIndian.setOnClickListener(categoryClickListener);
        if (btnDesserts != null) btnDesserts.setOnClickListener(categoryClickListener);
        if (btnBeverages != null) btnBeverages.setOnClickListener(categoryClickListener);

        // Image Slider Implementation
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageSlider imageSlider = findViewById(R.id.image_slider);
        if (imageSlider != null) {
            List<SlideModel> slideModels = new ArrayList<>();
            slideModels.add(new SlideModel(R.drawable.ima1, "Fresh and Tasty", ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.ima2, "Special Offer", ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.ima3, "Delicious Food", ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.ima4, "Great Discounts", ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.ima5, "Order Now", ScaleTypes.CENTER_CROP));

            imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
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
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
