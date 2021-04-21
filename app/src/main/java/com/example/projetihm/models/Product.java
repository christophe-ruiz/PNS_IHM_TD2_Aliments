package com.example.projetihm.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author Gabriel
 */
public class Product {
    private ImageView img;
    private String name;
    private String provenance;
    private double prix;
    private String desc;
    boolean isBio=false;
    boolean isLabel=false;

    public Product(ImageView img, String name, String provenance, double prix, String desc, boolean isBio, boolean isLabel) {
        this.img = img;
        this.name = name;
        this.provenance = provenance;
        this.prix = prix;
        this.desc = desc;
        this.isBio = isBio;
        this.isLabel = isLabel;
    }



}
