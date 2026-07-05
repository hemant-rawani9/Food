package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;

public class SettingsActivity extends AppCompatActivity {

    private MaterialSwitch switchDarkMode, switchNotifications;
    private MaterialButton btnRateUs, btnShareApp, btnResetSettings;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "settings_prefs";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_NOTIFICATIONS = "notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Initialize views
        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchNotifications = findViewById(R.id.switchNotifications);
        btnRateUs = findViewById(R.id.btnRateUs);
        btnShareApp = findViewById(R.id.btnShareApp);
        btnResetSettings = findViewById(R.id.btnResetSettings);

        // Load saved states
        loadSettings();

        // Dark Mode Toggle
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            savePreference(KEY_DARK_MODE, isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Notifications Toggle
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            savePreference(KEY_NOTIFICATIONS, isChecked);
            String status = isChecked ? "Notifications Enabled" : "Notifications Disabled";
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        });

        // Rate Us
        btnRateUs.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });

        // Share App
        btnShareApp.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Food App");
            String shareMessage = "Check out this amazing Food App!\n\nhttps://play.google.com/store/apps/details?id=" + getPackageName();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        // Reset Settings
        btnResetSettings.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle(R.string.reset_settings)
                .setMessage(R.string.reset_settings_confirm)
                .setPositiveButton("Yes", (dialog, which) -> resetSettings())
                .setNegativeButton("No", null)
                .show());
    }

    private void loadSettings() {
        boolean isDarkMode = sharedPreferences.getBoolean(KEY_DARK_MODE, false);
        boolean isNotifications = sharedPreferences.getBoolean(KEY_NOTIFICATIONS, true);

        switchDarkMode.setChecked(isDarkMode);
        switchNotifications.setChecked(isNotifications);
    }

    private void savePreference(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    private void resetSettings() {
        sharedPreferences.edit().clear().apply();
        loadSettings();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Toast.makeText(this, "Settings Reset", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
