package com.example.projetihm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projetihm.models.Producer;
import com.example.projetihm.models.ProductAdapter;

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

        RecyclerView products = findViewById(R.id.product_container);
        products.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildLayoutPosition(view) == 0)
                    outRect.top = 10;
                outRect.bottom = 20;
            }
        });
        ProductAdapter adapter = new ProductAdapter(producer.getProducts());
        products.setAdapter(adapter);
        products.setLayoutManager(new LinearLayoutManager(this));

        producer_name.setText(producer.getName());
        timeSlot.setText(producer.getTimeSlot());
        outletKind.setText(producer.getOutletKind());
        road.setText("Rue: " + producer.getRoad());
        city.setText("à: " + producer.getCity());

    }
}