package com.example.projetihm.producer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.projetihm.R;
import com.example.projetihm.models.Producer;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        findViewById(R.id.addButton).setOnClickListener(click->{
            Intent addProductIntent=new Intent(getApplicationContext(), AddProductActivity.class);
            startActivity(addProductIntent);
        });
        findViewById(R.id.producer_Orders_backButton).setOnClickListener(click->{
            Intent backIntent=new Intent(getApplicationContext(), ProducerActivity.class);
            startActivity(backIntent);
        });
    }
}