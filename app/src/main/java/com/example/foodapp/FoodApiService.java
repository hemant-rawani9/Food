package com.example.foodapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApiService {
    @GET("search.php?s=Pizza")
    Call<MealResponse> getMeals();

    @GET("categories.php")
    Call<FoodCategoryResponse> getCategories();
}
