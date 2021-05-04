package com.example.projetihm.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetihm.R;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;

    public ProductAdapter (List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View productView = inflater.inflate(R.layout.product, parent, false);
        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = this.products.get(position);
        DecimalFormat df = new DecimalFormat("#,##0.00");

        TextView name = holder.name;
        TextView description = holder.description;
        TextView price = holder.price;
        TextView origin = holder.origin;
        ImageView img = holder.img;
        ImageView organic = holder.organic;

        name.setText(p.getName());
        description.setText(p.getDesc());
        price.setText(df.format(p.getPrix()));
        origin.setText(p.getProvenance());
        img.setImageBitmap(p.getImg());
        organic.setVisibility(p.isBio ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView description;
        private final TextView price;
        private TextView origin;
        private ImageView img;
        private ImageView organic;

        public ViewHolder (View v) {
            super(v);
            name = v.findViewById(R.id.product_info_name);
            description = v.findViewById(R.id.product_info_description);
            price = v.findViewById(R.id.product_info_price);
            origin = v.findViewById(R.id.product_info_origin_country);
            img = v.findViewById(R.id.product_image);
            organic = v.findViewById(R.id.isOrganic);
        }
    }
}
