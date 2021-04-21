package com.example.projetihm.producer.tab.products;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projetihm.R;
import com.example.projetihm.models.Manager;
import com.example.projetihm.models.Product;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_products);
        this.retreiveProducts();
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

    public void retreiveProducts(){
        for(Product product : Manager.onSale){
            product.showPicture((View) findViewById(R.id.productPage));
        }
    }
}