package com.example.projetihm.models;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.projetihm.R;

import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlLineString;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.bonuspack.kml.KmlPoint;
import org.osmdroid.bonuspack.kml.KmlPolygon;
import org.osmdroid.bonuspack.kml.KmlTrack;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

public class ProducerMarkerStyler implements KmlFeature.Styler {
    private final Context context;
    private final MapView mapView;

    public ProducerMarkerStyler(Context c, MapView mapView) {
        this.context = c;
        this.mapView = mapView;
    }

    @Override
    public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

    }

    @Override
    public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
        Producer p = new Producer(kmlPlacemark);
        marker.setIcon(ContextCompat.getDrawable(this.context, R.drawable.marker));
//        marker.setTitle(kmlPlacemark.getExtendedData("societe_producteur"));
//        marker.setSubDescription(kmlPlacemark.getExtendedData("libelle_point_vente"));
        marker.setInfoWindow(new ProducerMarkerInfoWindow(R.layout.producer_marker_info_window, mapView, p));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {

                return true;
            }
        });
    }

    @Override
    public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

    }

    @Override
    public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

    }

    @Override
    public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

    }
}
