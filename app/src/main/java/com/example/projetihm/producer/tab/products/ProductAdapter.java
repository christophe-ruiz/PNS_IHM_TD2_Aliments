package com.example.projetihm.producer.tab.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projetihm.R;
import com.example.projetihm.models.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    ArrayList<Product> products;
    private LayoutInflater mainInflater;
    private IProductAdapterListener listener;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.products = products;
        this.mainInflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout ;
        layout= (LinearLayout) (convertView==null ? mainInflater.inflate(R.layout.adapter_product,parent,false):convertView);
        ((TextView) layout.findViewById(R.id.productListName)).setText(products.get(position).getName());
        ((ImageView) layout.findViewById(R.id.productListImg)).setImageBitmap(products.get(position).getImg());
        layout.setOnClickListener(click->{
            listener.onClickProduct(products.get(position));
        });
        return layout;
    }

    public void addListener(IProductAdapterListener listener){
        this.listener=listener;
    }
}
