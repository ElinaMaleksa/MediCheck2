package com.app.medicheck.ui.home;

public class Products {
    //String category;
    String name;
    //String ingredients;
    String bestBefore;
    //String serialNumber;

    public Products(String name, String bestBefore) {
        this.name = name;
        this.bestBefore = bestBefore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBestBefore() {
        return bestBefore;
    }
}
