package com.example.foodapp;

import java.util.List;

public class FoodCategoryResponse {
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public static class Category {
        private String idCategory;
        private String strCategory;
        private String strCategoryThumb;
        private String strCategoryDescription;

        public String getIdCategory() { return idCategory; }
        public String getStrCategory() { return strCategory; }
        public String getStrCategoryThumb() { return strCategoryThumb; }
        public String getStrCategoryDescription() { return strCategoryDescription; }

        public String getId() { return idCategory; }
        public String getName() { return strCategory; }
    }
}
