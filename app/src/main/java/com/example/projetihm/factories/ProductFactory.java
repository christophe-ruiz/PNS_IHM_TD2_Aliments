package com.example.projetihm.factories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.projetihm.models.Product;
import com.example.projetihm.models.Timetable;
import com.example.projetihm.models.users.Seller;
import com.example.projetihm.models.users.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class ProductFactory {
    public static final String NAME = "name";
    public static final String PRICE = "prix";
    public static final String ORIGIN = "provenance";
    public static final String DESC = "desc";
    public static final String IMG = "img";
    public static final String BIO = "isBio";
    public static final String LABEL = "isLabel";


    public static Product build(JSONObject object) throws JSONException {
        String name = object.getString(NAME);
        double prix = object.getDouble(PRICE);
        String origin = object.getString(ORIGIN);
        String desc = object.getString(DESC);
        Bitmap img = null;
        boolean bio = object.getBoolean(BIO);
        boolean label = object.getBoolean(LABEL);

        try {
            File file = new File(object.getString(IMG));
            img = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            Log.d("Projet IHM", e.getMessage());
        }

        return new Product(img,name,origin,prix,desc,bio,label);
    }
}
