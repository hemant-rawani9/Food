package com.example.foodapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    TextView tvLogin;
    EditText etName, etEmail, etMobile, etPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Edge to edge padding fix
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Database
        dbHelper = new DatabaseHelper(this);

        // Link XML views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        // Register button click
        btnRegister.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String mobile = etMobile.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // basic validation
            if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.checkEmail(email)) {
                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = dbHelper.insertUser(name, email, mobile, password);

            if (result) {
                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                finish(); // go back to login
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Go back to login screen
        tvLogin.setOnClickListener(v -> finish());
    }
}