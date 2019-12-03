package com.app.medicheck.ui.home;

public class Products {
    //String category;
    String name;
    String ingredients;
    String bestBefore;
    //String serialNumber;

    public Products(String name, String ingredients, String bestBefore) {
        this.name = name;
        this.ingredients = ingredients;
        this.bestBefore = bestBefore;
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
