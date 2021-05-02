package com.example.projetihm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.projetihm.models.Producer;

public class ProducerPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.producer_info_title);
        Producer producer = getIntent().getExtras().getParcelable("producer");

        setContentView(R.layout.activity_producer_panel);

        TextView producer_name = findViewById(R.id.producer_panel);
        TextView timeSlot = findViewById(R.id.timeSlot_panel);
        TextView outletKind = findViewById(R.id.outlet_panel);
        TextView road = findViewById(R.id.road_panel);
        TextView city = findViewById(R.id.city_panel);

        producer_name.setText(producer.getName());
        timeSlot.setText(producer.getTimeSlot());
        outletKind.setText(producer.getOutletKind());
        road.setText("Rue: " + producer.getRoad());
        city.setText("à: " + producer.getCity());
    }
}