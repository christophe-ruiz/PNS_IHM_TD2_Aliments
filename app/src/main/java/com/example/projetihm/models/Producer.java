package com.example.projetihm.models;

import org.osmdroid.bonuspack.kml.KmlPlacemark;

import java.util.ArrayList;

/**
 * @author Christophe
 */
public class Producer {

    private final String city;
    private final String id;

    private final String outletKind;
    private final String name;
    private final String road;
    private final String timeSlot;

    Producer(KmlPlacemark kml) {
        this.city = kml.getExtendedData("commune");
        this.id = kml.getExtendedData("id_pdv");
        this.outletKind = kml.getExtendedData("libelle_point_vente");
        this.name = kml.getExtendedData("societe_producteur");
        this.road = kml.getExtendedData("voie");
        this.timeSlot = kml.getExtendedData("creneau");
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }

    public String getOutletKind() {
        return outletKind;
    }

    public String getName() {
        return name;
    }

    public String getRoad() {
        return road;
    }

    public String getTimeSlot() {
        return timeSlot;
    }
}
