package com.example.projetihm.models;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetihm.MainActivity;
import com.example.projetihm.ProducerPanel;
import com.example.projetihm.R;

import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class ProducerMarkerInfoWindow extends InfoWindow {
    private final Producer producer;
    private final MapView mapView;

    public ProducerMarkerInfoWindow(int layoutResId, MapView mapView, Producer producer) {
        super(layoutResId, mapView);
        this.producer = producer;
        this.mapView = mapView;
    }

    @Override
    public void onOpen(Object item) {
        InfoWindow.closeAllInfoWindowsOn(this.mapView);
        TextView producer = mView.findViewById(R.id.producer);
        TextView timeSlot = mView.findViewById(R.id.timeSlot);
//        Button button = mView.findViewById(R.id.more_info);

        producer.setText(this.producer.getName());
        timeSlot.setText(this.producer.getTimeSlot());
//        button.setOnClickListener(v -> {
//            Intent i = new Intent(v.getContext(), ProducerPanel.class);
//            v.getContext().startActivity(i);
//        });
    }

    @Override
    public void onClose() {
        super.close();
    }
}
