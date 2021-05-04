package com.example.projetihm.models;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Manager {
    public static ArrayList<Product> onSale = new ArrayList<>();

    public static ArrayList<Product> getOnSale() {
        return onSale;
    }

}
