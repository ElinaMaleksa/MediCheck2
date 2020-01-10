package com.app.medicheck.ui.home;

public class Products {
    String category;
    String name;
    String ingredients;
    String bestBefore;
    String serialNumber;
    int id;

    public Products(int id, String name, String category, String ingredients, String bestBefore, String serialNumber) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.bestBefore = bestBefore;
        this.serialNumber = serialNumber;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public int getId() {
        return id;
    }
}
