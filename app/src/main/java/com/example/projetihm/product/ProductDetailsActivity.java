package com.example.projetihm.product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetihm.R;
import com.example.projetihm.models.Product;

import org.w3c.dom.Text;

public class ProductDetailsActivity extends AppCompatActivity {
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        product=getIntent().getParcelableExtra("product");

        ((TextView)findViewById(R.id.productTitle)).setText(product.getName());
        ((TextView)findViewById(R.id.productPrice)).setText(Double.toString(product.getPrix())+" €");
        ((TextView)findViewById(R.id.productDesc)).setText(product.getDesc());
        ((TextView)findViewById(R.id.productOrigin)).setText(product.getProvenance());
        ((ImageView)findViewById(R.id.productImg)).setImageBitmap(product.getImg());
        if(!product.isBio()){
            TextView bio = findViewById(R.id.productBio);
            bio.setVisibility(View.INVISIBLE);
        }
        if(!product.isLabel()){
            TextView bio = findViewById(R.id.productLabel);
            bio.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}