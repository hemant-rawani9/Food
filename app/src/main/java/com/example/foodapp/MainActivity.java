package com.example.foodapp;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import androidx.annotation.NonNull;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fetchCategories();
    }

    private void fetchCategories() {
        FoodApiService apiService = RetrofitClient.getApiService();
        Call<FoodCategoryResponse> call = apiService.getCategories();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<FoodCategoryResponse> call, @NonNull Response<FoodCategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FoodCategoryResponse.Category> categories = response.body().getCategories();

                    for (FoodCategoryResponse.Category category : categories) {
                        Log.d("API_DATA", "ID: " + category.getId() + ", Name: " + category.getName());
                    }
                } else {
                    Log.e("API_ERROR", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FoodCategoryResponse> call, @NonNull Throwable t) {
                Log.e("API_ERROR", "Network failure: " + t.getMessage());
            }
        });
    }
}
