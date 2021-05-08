package com.example.projetihm.producer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.projetihm.R;
import com.example.projetihm.producer.tab.products.ProductsActivity;

public class ProducerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        assert getSupportActionBar() != null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        (findViewById(R.id.myProducts)).setOnClickListener(click->{
            Intent ordersIntent= new Intent(getApplicationContext(), ProductsActivity.class);
            startActivity(ordersIntent);
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}