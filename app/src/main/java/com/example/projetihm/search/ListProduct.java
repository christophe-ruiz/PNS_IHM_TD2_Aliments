package com.example.projetihm.search;

import com.example.projetihm.models.Manager;
import com.example.projetihm.models.Product;

import java.util.ArrayList;

public class ListProduct {
    String product;
    ArrayList<Product> listeProducts;


    public ListProduct(String product) {
        this.product = product;
        listeProducts = new ArrayList<>();
    }

    public void construireListe() {
        ArrayList<Product> produits = Manager.getOnSale();
        for(Product p : produits) {
            if(p.getName().toLowerCase().contains(product.toLowerCase()))
                listeProducts.add(p);
        }
    }

    public void add(Product d) {
        listeProducts.add(d);
    }

    public Product get(int i) {
        return listeProducts.get(i);
    }

    public int size() {
        return listeProducts.size();
    }

}
