package com.example.projetihm.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * @author Gabriel
 */
public class Product implements Parcelable {

    private final Bitmap img;
    private int imgId = 0;
    private final String name;
    private final String provenance;
    private final double prix;
    private final String desc;
    boolean isBio;
    boolean isLabel;


    protected Product(Parcel in) {
        img = in.readParcelable(Bitmap.class.getClassLoader());
        name = in.readString();
        provenance = in.readString();
        prix = in.readDouble();
        desc = in.readString();
        isBio = in.readByte() != 0;
        isLabel = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public Product(Bitmap img, String name, String provenance, double prix, String desc, boolean isBio, boolean isLabel) {
        this.img = img;
        this.name = name;
        this.provenance = provenance;
        this.prix = prix;
        this.desc = desc;
        this.isBio = isBio;
        this.isLabel = isLabel;
    }
    public Product(int imgId, String name, String provenance, double price, String desc, boolean isBio, boolean isLabel) {
        this (null, name, provenance, price, desc, isBio, isLabel);
        this.imgId = imgId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(img, flags);
        dest.writeString(name);
        dest.writeString(provenance);
        dest.writeDouble(prix);
        dest.writeString(desc);
        dest.writeByte((byte) (isBio ? 1 : 0));
        dest.writeByte((byte) (isLabel ? 1 : 0));
    }

    public Bitmap getImg() {
        return img;
    }

    public int getImgId() {
        return imgId;
    }

    public String getName() {
        return name;
    }

    public String getProvenance() {
        return provenance;
    }

    public double getPrix() {
        return prix;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isBio() {
        return isBio;
    }

    public boolean isLabel() {
        return isLabel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return this.prix==((Product) o).getPrix() &&
                isBio == product.isBio() &&
                isLabel == product.isLabel() &&
               name.equals(((Product) o).getName()) &&
                provenance.equals(((Product) o).getProvenance()) &&
                desc.equals(((Product) o).getDesc());
    }

    @Override
    public int hashCode() {
        return Objects.hash(img, name, provenance, prix, desc, isBio, isLabel);
    }

    @Override
    public String toString() {
        String returned=  name + ": " +
                "prix " + prix+" €";
        if(isBio)
            returned+=", Bio";
        returned+=".";
        return returned;
    }
}
