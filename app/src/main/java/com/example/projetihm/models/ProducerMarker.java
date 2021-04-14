package com.example.projetihm.models;

import android.content.Context;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class ProducerMarker {
    private Marker marker;

    // TODO: Ajouter un paramètre Producer et récupérer sa position, son titre et sa description.
    public ProducerMarker (MapView map) {
        this.marker = new Marker(map);
        this.marker.setPosition(new GeoPoint(43.545795399999996, 5.0402841));
        this.marker.setTitle("Vendeur de substances");
        this.marker.setSubDescription("C'est pas cher askip");
    }

    public Marker getMarker() {
        return marker;
    }
}
