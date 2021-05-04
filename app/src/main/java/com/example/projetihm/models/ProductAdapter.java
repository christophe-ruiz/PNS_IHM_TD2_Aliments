package com.example.projetihm.models;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
        View productView = inflater.inflate(R.layout.product_choice, parent, false);
        return new ViewHolder(productView, inflater);
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

        EditText value = ((EditText) holder.self.findViewById(R.id.editTextNumber));
        value.setText("0");

        holder.self.findViewById(R.id.sub_product).setOnClickListener(v -> {
            int baseVal = Integer.parseInt(value.getText().toString());
            value.setText(String.valueOf(baseVal > 0 ? --baseVal : 0));
        });

        holder.self.findViewById(R.id.add_product).setOnClickListener(v -> {
            int baseVal = Integer.parseInt(value.getText().toString());
            value.setText(String.valueOf(++baseVal));
        });

        holder.self.findViewById(R.id.add_product_to_basket).setOnClickListener(v -> {
            int howMany = Integer.parseInt(value.getText().toString());
            Basket.getInstance().add(p, howMany);
            Toast.makeText(value.getContext(), "Ajout au panier effectué !", Toast.LENGTH_LONG).show();
        });

        name.setText(p.getName());
        description.setText(p.getDesc());
        price.setText(df.format(p.getPrix()));
        origin.setText(p.getProvenance());

        if (p.getImg() != null) {
            Log.d("Oeoe", "C'est pas null");
            img.setImageBitmap(p.getImg());
        } else if (p.getImgId() != 0) {
            Log.d("Oeoe", String.valueOf(p.getImgId()));
            img.setImageResource(p.getImgId());
        } else {
            img.setVisibility(View.INVISIBLE);
        }

        organic.setVisibility(p.isBio ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final View subView;
        private final View self;
        private final TextView description;
        private final TextView price;
        private TextView origin;
        private ImageView img;
        private ImageView organic;

        public ViewHolder (View v, LayoutInflater inflater) {
            super(v);
            ConstraintLayout layout = v.findViewById(R.id.product_to_choose);
            subView = inflater.inflate(R.layout.product, layout, false);
            layout.addView(subView);

            self = v;
            name = subView.findViewById(R.id.product_info_name);
            description = subView.findViewById(R.id.product_info_description);
            price = subView.findViewById(R.id.product_info_price);
            origin = subView.findViewById(R.id.product_info_origin_country);
            img = subView.findViewById(R.id.product_image);
            organic = subView.findViewById(R.id.isOrganic);
        }
    }
}
