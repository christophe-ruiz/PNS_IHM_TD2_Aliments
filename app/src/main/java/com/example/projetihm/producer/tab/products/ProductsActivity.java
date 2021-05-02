package com.example.projetihm.producer.tab.products;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projetihm.R;
import com.example.projetihm.models.Manager;
import com.example.projetihm.models.Product;
import com.example.projetihm.product.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity implements IProductAdapterListener{

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
    protected void onResume() {
        super.onResume();
        ProductAdapter adapter = new ProductAdapter(getApplicationContext(),Manager.onSale);
        adapter.notifyDataSetChanged();
        ((ListView)findViewById(R.id.productsList)).setAdapter(adapter);
        adapter.addListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onClickProduct(Product product) {
        Intent productIntent = new Intent(getApplicationContext(),ProductDetailsActivity.class);
        productIntent.putExtra("product",(Parcelable)product);
        this.startActivity(productIntent);
    }
}