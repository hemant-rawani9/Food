package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity {

    private EditText cardNumber, month, year, cvv, Address, PinCode, upiId, fullName, mobileNumber;
    private RadioGroup paymentMethodGroup;
    private View cardDetailsSection, upiDetailsSection;
    private Spinner citySpinner;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        dbHelper = new DatabaseHelper(this);

        Button purchasebutton;
        purchasebutton = findViewById(R.id.purchasebutton);
        cardNumber = findViewById(R.id.cardNumber);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        cvv = findViewById(R.id.cvv);
        Address = findViewById(R.id.Address);
        PinCode = findViewById(R.id.PinCode);
        upiId = findViewById(R.id.upiId);
        fullName = findViewById(R.id.fullName);
        mobileNumber = findViewById(R.id.mobileNumber);
        
        paymentMethodGroup = findViewById(R.id.paymentMethodGroup);
        cardDetailsSection = findViewById(R.id.cardDetailsSection);
        upiDetailsSection = findViewById(R.id.upiDetailsSection);
        citySpinner = findViewById(R.id.citySpinner);

        // Setup City Spinner
        ArrayAdapter<String> adapter = getStringArrayAdapter();
        citySpinner.setAdapter(adapter);

        // Toggle Details Sections based on selection
        paymentMethodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioCard) {
                cardDetailsSection.setVisibility(View.VISIBLE);
                upiDetailsSection.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioUPI) {
                cardDetailsSection.setVisibility(View.GONE);
                upiDetailsSection.setVisibility(View.VISIBLE);
            } else {
                cardDetailsSection.setVisibility(View.GONE);
                upiDetailsSection.setVisibility(View.GONE);
            }
        });

        purchasebutton.setOnClickListener(v -> {
            int selectedPaymentId = paymentMethodGroup.getCheckedRadioButtonId();
            
            if (selectedPaymentId == R.id.radioCard) {
                if (cardNumber.getText().toString().isEmpty()) {
                    cardNumber.setError("Enter Card Number");
                    return;
                }
                if (month.getText().toString().isEmpty()) {
                    month.setError("Enter Month");
                    return;
                }
                if (year.getText().toString().isEmpty()) {
                    year.setError("Enter Year");
                    return;
                }
                if (cvv.getText().toString().isEmpty()) {
                    cvv.setError("Enter CVV");
                    return;
                }
            } else if (selectedPaymentId == R.id.radioUPI) {
                if (upiId.getText().toString().isEmpty()) {
                    upiId.setError("Enter UPI ID");
                    return;
                }
            }

            String name = fullName.getText().toString().trim();
            String mobile = mobileNumber.getText().toString().trim();
            String addressStr = Address.getText().toString().trim();
            String pinStr = PinCode.getText().toString().trim();

            if (name.isEmpty()) {
                fullName.setError("Enter Full Name");
                return;
            }
            if (mobile.isEmpty()) {
                mobileNumber.setError("Enter Mobile Number");
                return;
            }
            if (addressStr.isEmpty()) {
                Address.setError("Enter Address");
                return;
            }
            if (pinStr.isEmpty()) {
                PinCode.setError("Enter Pin Code");
                return;
            }
            if (citySpinner.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please select a city", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullAddress = name + "\n" + mobile + "\n" + addressStr + ", " + citySpinner.getSelectedItem().toString() + " - " + pinStr;
            String paymentMethod = "Unknown";
            RadioButton rb = findViewById(selectedPaymentId);
            if (rb != null) {
                paymentMethod = rb.getText().toString();
            }

            // Success logic
            saveOrderToDatabase(fullAddress, paymentMethod);
            Intent i = new Intent(Checkout.this, Success.class);
            startActivity(i);
            finish();
        });
    }

    private ArrayAdapter<String> getStringArrayAdapter() {
        List<String> cities = new ArrayList<>();
        cities.add("Select City");
        cities.add("Dhanbad");
        cities.add("Ranchi");
        cities.add("Dumka");
        cities.add("Deoghar");
        cities.add("Jamtara");
        cities.add("Godda");
        cities.add("Rampurhat");
        cities.add("Pakur");
        cities.add("Mumbai");
        cities.add("Delhi");
        cities.add("Bangalore");
        cities.add("Hyderabad");
        cities.add("Ahmedabad");
        cities.add("Chennai");
        cities.add("Kolkata");
        cities.add("Pune");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void saveOrderToDatabase(String fullAddress, String paymentMethod) {
        CartManager cartManager = CartManager.getInstance();
        
        double total = cartManager.getTotalPrice();
        java.util.List<Product> items = cartManager.getCartItems();
        
        StringBuilder itemsBuilder = new StringBuilder();
        for (Product p : items) {
            itemsBuilder.append(p.getName()).append(", ");
        }
        
        String itemsStr = itemsBuilder.toString();
        if (itemsStr.endsWith(", ")) {
            itemsStr = itemsStr.substring(0, itemsStr.length() - 2);
        }
        
        String date = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm", java.util.Locale.getDefault()).format(new java.util.Date());
        
        dbHelper.insertOrder(date, "₹ " + total, itemsStr, fullAddress, paymentMethod);
        cartManager.clearCart();
    }
}
