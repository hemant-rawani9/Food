package com.example.foodapp;

@SuppressWarnings("unused")
public class Product {
    private int id;
    private String name;
    private String price;
    private String description;
    private String photo;
    private int imageResource;
    private String category;

    public Product() {
    }

    public Product(int id, String price, String name, int imageResource, String category, String description) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageResource = imageResource;
        this.category = category;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
