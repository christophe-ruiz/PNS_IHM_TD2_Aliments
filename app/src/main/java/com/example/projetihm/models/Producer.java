package com.example.projetihm.models;

import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.projetihm.R;

import org.osmdroid.bonuspack.kml.KmlPlacemark;

import java.util.ArrayList;


/**
 * @author Christophe
 */
public class Producer implements Parcelable {
    private final String city;
    private final String id;

    private final String outletKind;
    private final String name;
    private final String road;
    private final String timeSlot;

    private ArrayList<Product> products = new ArrayList<Product>() {{
        add(new Product(null, "Pomme Rouge", "France", 2.00, "Jolie pomme rouge", true, false));
        add(new Product(null, "Pomme Verte", "Espagne", 1.00, "Jolie pomme verte", false, false));
    }};

    Producer(KmlPlacemark kml) {
        this.city = kml.getExtendedData("commune");
        this.id = kml.getExtendedData("id_pdv");
        this.outletKind = kml.getExtendedData("libelle_point_vente");
        this.name = kml.getExtendedData("societe_producteur");
        this.road = kml.getExtendedData("voie");
        this.timeSlot = kml.getExtendedData("creneau");
    }

    protected Producer(Parcel in) {
        city = in.readString();
        id = in.readString();
        name = in.readString();
        outletKind = in.readString();
        road = in.readString();
        timeSlot = in.readString();
    }

    public static final Creator<Producer> CREATOR = new Creator<Producer>() {
        @Override
        public Producer createFromParcel(Parcel in) {
            return new Producer(in);
        }

        @Override
        public Producer[] newArray(int size) {
            return new Producer[size];
        }
    };

    public ArrayList<Product> getProducts() {
        return products;
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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.outletKind);
        dest.writeString(this.road);
        dest.writeString(this.timeSlot);
    }
}
