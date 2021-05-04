package com.example.projetihm.search;

import com.example.projetihm.models.Product;

import java.util.ArrayList;

public class ListProduct {
    Product product;
    ArrayList<String> listeProducts;

    public ListProduct(Product product) {
        this.product = product;
        listeProducts = new ArrayList<>();
    }

    public void construireListe() {
        for(int i = 1; i<=10;i++) {
            String calcul = nb + " x " + i + " = " + (nb * i);
            listeProducts.add(calcul);
        }
    }

    public void add(String d) {
        listeProducts.add(d);
    }

    public String get(int i) {
        return listeProducts.get(i);
    }

    public int size() {
        return listeProducts.size();
    }

}
