package com.example.projetihm.models;



import android.util.Log;

import com.example.projetihm.factories.ProductFactory;
import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.models.users.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;


public class Manager {
    public static ArrayList<Product> onSale = new ArrayList<>();

    public static ArrayList<Product> getOnSale() {
        return onSale;
    }

    public static List<JsonConvertible> getJsonProducts(){
        ArrayList<JsonConvertible> json = new ArrayList<>();
        json.addAll(onSale);
        return json;
    }

    public static void loadProducts (String jsonFileContent) {

        try {
            JSONArray object = new JSONArray(jsonFileContent);
            for(int i=0;i<object.length();i++){
                onSale.add(ProductFactory.build(object.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.d("Projet IHM", e.getMessage());
        }
    }

}
