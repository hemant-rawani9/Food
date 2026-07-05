package com.example.foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    private EditText etProfileName, etProfileEmail, etProfileMobile, etNewPassword;
    private Button btnUpdateProfile, btnChangePassword, btnLogout;
    private ImageView ivProfileImage;

    private DatabaseHelper dbHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Session
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "");

        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Session expired, please login again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Initialize DB
        dbHelper = new DatabaseHelper(this);

        // Initialize Views
        ivProfileImage = findViewById(R.id.ivProfileImage);
        etProfileName = findViewById(R.id.etProfileName);
        etProfileEmail = findViewById(R.id.etProfileEmail);
        etProfileMobile = findViewById(R.id.etProfileMobile);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnLogout = findViewById(R.id.btnLogout);

        // Load User Data
        loadUserData();

        // Image Picker
        ivProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        // Update Profile logic
        btnUpdateProfile.setOnClickListener(v -> {
            String name = etProfileName.getText().toString().trim();
            String mobile = etProfileMobile.getText().toString().trim();

            if (name.isEmpty() || mobile.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.updateProfile(userEmail, name, mobile)) {
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });

        // Change Password logic
        btnChangePassword.setOnClickListener(v -> {
            String newPass = etNewPassword.getText().toString().trim();
            if (newPass.isEmpty()) {
                etNewPassword.setError("Enter new password");
                return;
            }

            if (dbHelper.updatePassword(userEmail, newPass)) {
                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                etNewPassword.setText("");
            } else {
                Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show();
            }
        });

        // Logout logic
        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadUserData() {
        Cursor cursor = dbHelper.getUserData(userEmail);
        if (cursor != null && cursor.moveToFirst()) {
            etProfileName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)));
            etProfileEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EMAIL)));
            etProfileMobile.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_MOBILE)));
            cursor.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            ivProfileImage.setImageURI(imageUri);
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
            // Note: In a real app, you would save this URI or the image file path to the DB/Preferences
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
