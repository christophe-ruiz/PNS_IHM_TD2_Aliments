package com.example.projetihm.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Gabriel
 */
public class Product implements Parcelable {
    private static final Map<String, Bitmap> images = new HashMap<>();
    private final Bitmap img;
    private int imgId = 0;
    private final String name;
    private final String provenance;
    private final double prix;
    private final String desc;
    boolean isBio;
    boolean isLabel;


    protected Product(Parcel in) {
        name = in.readString();
        provenance = in.readString();
        prix = in.readDouble();
        desc = in.readString();
        isBio = in.readByte() != 0;
        isLabel = in.readByte() != 0;
        imgId = in.readInt();
        img = images.remove(name);
        Log.d("Test", "ImgId IN : " + imgId);
        Log.d("Test", "Img IN :" + img);
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
        Log.d("Test", "Img bitmap :" + img);
    }

    public Product(int imgId, String name, String provenance, double price, String desc, boolean isBio, boolean isLabel) {
        this (null, name, provenance, price, desc, isBio, isLabel);
        Log.d("Test", "ImgId imgId :" + imgId);
        this.imgId = imgId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        images.put(name, img);
        dest.writeString(name);
        dest.writeString(provenance);
        dest.writeDouble(prix);
        dest.writeString(desc);
        dest.writeInt(imgId);
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
}
