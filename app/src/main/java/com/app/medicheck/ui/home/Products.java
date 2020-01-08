package com.app.medicheck.ui.home;

public class Products {
    String category;
    String name;
    String ingredients;
    String bestBefore;
    String serialNumber;
    int id;

    public Products(int id, String name,String category, String ingredients, String bestBefore,String serialNumber) {
        this.name = name;
        this.ingredients = ingredients;
        this.bestBefore = bestBefore;
        this.category = category;
        this.serialNumber = serialNumber;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getBestBefore() {
        return bestBefore;
    }

    public String getIngredients() {
        return ingredients;
    }
}
