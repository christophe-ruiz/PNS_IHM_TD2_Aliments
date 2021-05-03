package com.example.projetihm.models;



import java.util.ArrayList;


public class Manager {
    public static ArrayList<Product> onSale = new ArrayList<>();

    public static ArrayList<Product> getOnSale() {
        return onSale;
    }
}
