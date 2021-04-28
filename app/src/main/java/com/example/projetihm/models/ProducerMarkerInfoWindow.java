package com.example.projetihm.models;

import android.widget.TextView;

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
        producer.setText(this.producer.getName());

        TextView timeSlot = mView.findViewById(R.id.timeSlot);
        timeSlot.setText(this.producer.getTimeSlot());
    }

    @Override
    public void onClose() {
        super.close();
    }
}
