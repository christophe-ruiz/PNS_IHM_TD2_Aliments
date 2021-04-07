package com.example.projetihm.producer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.projetihm.R;

public class ProducerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer);
        (findViewById(R.id.orderButton)).setOnClickListener(click->{
            Intent ordersIntent= new Intent(getApplicationContext(),OrdersActivity.class);
            startActivity(ordersIntent);
        });
    }
}