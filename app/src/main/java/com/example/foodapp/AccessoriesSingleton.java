package com.example.foodapp;
import java.util.ArrayList;
import java.util.List;

public class AccessoriesSingleton {

    private final List<Product> productList = new ArrayList<>();
    private static AccessoriesSingleton mProductSingleton;


    public static synchronized AccessoriesSingleton get() {

        if (mProductSingleton == null) {
            mProductSingleton = new AccessoriesSingleton();

        }
        return mProductSingleton;

    }

    private AccessoriesSingleton() {
        // Pizza
        productList.add(new Product(1, "₹ 250", "Margherita Pizza", R.drawable.margpizz, "Pizza", "Classic tomato and mozzarella cheese pizza."));
        productList.add(new Product(2, "₹ 350", "Peperoni Pizza", R.drawable.peppiza, "Pizza", "Spicy pepperoni with extra cheese."));
        productList.add(new Product(13, "₹ 300", "Veg Pizza", R.drawable.vegpiza, "Pizza", "Loaded with fresh vegetables and olives."));
        productList.add(new Product(14, "₹ 400", "Chicken Pizza", R.drawable.chikenpizza, "Pizza", "Grilled chicken toppings with BBQ sauce."));

        // Burger
        productList.add(new Product(3, "₹ 150", "Veggie Burger", R.drawable.vegnurp, "Burger", "Crispy veg patty with fresh lettuce and mayo."));
        productList.add(new Product(4, "₹ 180", "Cheese Burger", R.drawable.chikenburd, "Burger", "Juicy patty with melted cheese and pickles."));
        productList.add(new Product(15, "₹ 220", "Chicken Burger", R.drawable.cheesedu, "Burger", "Grilled chicken breast with spicy sauce."));
        productList.add(new Product(16, "₹ 250", "Zinger Burger", R.drawable.zinburger, "Burger", "Extra crunchy chicken burger with zesty mayo."));

        // Chinese
        productList.add(new Product(5, "₹ 200", "Noodles", R.drawable.vegnd, "Chinese", "Classic stir-fried vegetable noodles."));
        productList.add(new Product(6, "₹ 220", "Manchurian", R.drawable.manchurain, "Chinese", "Fried veg balls in tangy Manchurian sauce."));
        productList.add(new Product(17, "₹ 180", "Spring Rolls", R.drawable.sprl, "Chinese", "Crispy rolls stuffed with seasoned vegetables."));
        productList.add(new Product(18, "₹ 240", "Fried Rice", R.drawable.vegrc, "Chinese", "Aromatic rice tossed with veggies and soy sauce."));

        // South Indian
        productList.add(new Product(7, "₹ 120", "Masala Dosa", R.drawable.msds, "South Indian", "Crispy rice crepe with spicy potato filling."));
        productList.add(new Product(8, "₹ 100", "Idli Sambhar", R.drawable.idli, "South Indian", "Soft idlis served with hot sambhar and chutney."));
        productList.add(new Product(19, "₹ 110", "Vada Sambhar", R.drawable.vdsa, "South Indian", "Crispy lentil donuts served with sambhar."));
        productList.add(new Product(20, "₹ 130", "Uttapam", R.drawable.utp, "South Indian", "Thick rice pancake topped with onions and chillies."));
        productList.add(new Product(25, "₹ 130", "Medu Vada", R.drawable.mdv, "South Indian", "Traditional crunchy deep-fried lentil snack."));

        // Desserts
        productList.add(new Product(9, "₹ 150", "Cakes", R.drawable.cake1, "Desserts", "Freshly baked cake with rich cream frosting."));
        productList.add(new Product(10, "₹ 100", "Pastry", R.drawable.pastry, "Desserts", "Small slice of heaven with layered cream."));
        productList.add(new Product(21, "₹ 120", "Ice Cream", R.drawable.ice, "Desserts", "Creamy and cold delight in various flavors."));
        productList.add(new Product(22, "₹ 90", "Brownie", R.drawable.brownie, "Desserts", "Warm chocolate brownie with a fudge center."));
        productList.add(new Product(26, "₹ 180", "Gulab Jamun", R.drawable.gulabjamun, "Desserts", "Sweet fried dough balls soaked in sugar syrup."));

        // Beverages
        productList.add(new Product(11, "₹ 80", "Cold Coffee", R.drawable.cdcf, "Beverages", "Chilled coffee blended with ice cream."));
        productList.add(new Product(12, "₹ 50", "Coke", R.drawable.coke, "Beverages", "Refreshing classic Coca-Cola."));
        productList.add(new Product(23, "₹ 70", "Lemonade", R.drawable.lemonade, "Beverages", "Zesty and refreshing lemon drink."));
        productList.add(new Product(24, "₹ 60", "Iced Tea", R.drawable.iceta, "Beverages", "Cold brewed tea with a hint of lemon."));
    }


    public static AccessoriesSingleton getInstance() {
        return get();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getCategory() != null && product.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(product);
            }
        }
        return filteredList;
    }


    public Product getProduct(int productId) {
        for (Product product : productList) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }
}
