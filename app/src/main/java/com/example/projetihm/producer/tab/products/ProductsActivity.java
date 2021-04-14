package com.example.projetihm.producer.tab.products;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.projetihm.R;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_products);
        findViewById(R.id.addButton).setOnClickListener(click->{
            Intent addProductIntent=new Intent(getApplicationContext(), AddProductActivity.class);
            startActivity(addProductIntent);
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}