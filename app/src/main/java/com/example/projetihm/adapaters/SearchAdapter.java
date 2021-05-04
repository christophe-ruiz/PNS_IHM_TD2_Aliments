package com.example.projetihm.adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.projetihm.R;
import com.example.projetihm.models.Product;
import com.example.projetihm.search.ListProduct;

public class SearchAdapter extends BaseAdapter {
    private ListProduct listProduct;
    private Context context;
    private LayoutInflater layoutInflater;

    public SearchAdapter(Context context, ListProduct listCalcul) {
        this.context = context;
        this.listProduct = listCalcul;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return listProduct.size();
    }

    public Object getItem(int position) {
        return listProduct.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layout;

        if (convertView == null) {
            layout = (ConstraintLayout) layoutInflater.inflate(R.layout.product, parent, false);
        } else {
            layout = (ConstraintLayout) convertView;
        }

        final Product currentItem = (Product) getItem(position);

        ImageView productImg = layout.findViewById(R.id.product_image);
        if (currentItem.getImg() != null) {
            productImg.setImageBitmap(currentItem.getImg());
        } else if (currentItem.getImgId() != 0) {
            productImg.setImageResource(currentItem.getImgId());
        }

        ((TextView) layout.findViewById(R.id.product_info_name)).setText(currentItem.getName());
        ((TextView) layout.findViewById(R.id.product_info_description)).setText(currentItem.getDesc());
        ((TextView) layout.findViewById(R.id.product_info_origin_country)).setText(
                currentItem.getProvenance());
        ((TextView) layout.findViewById(R.id.product_info_price)).setText(
                String.valueOf(currentItem.getPrix()));

        layout.findViewById(R.id.isOrganic).setVisibility(
                (currentItem.isBio() ? View.VISIBLE : View.INVISIBLE));

        return layout;

    }

}
