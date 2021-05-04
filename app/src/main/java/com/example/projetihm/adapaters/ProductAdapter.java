package com.example.projetihm.adapaters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.projetihm.R;
import com.example.projetihm.models.Order;
import com.example.projetihm.models.Product;

import java.util.List;

/**
 * @author Gabriel
 */
public class ProductAdapter extends BaseAdapter {
	private final LayoutInflater inflater;
	private final List<Product> products;
	private final Order order;

	public ProductAdapter(Context context, Order order) {
		this.inflater = LayoutInflater.from(context);
		this.products = order.getProducts();
		this.order = order;
	}

	@Override
	public int getCount() {
		return products.size();
	}

	@Override
	public Object getItem(int position) {
		return products.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("SetTextI18n")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ConstraintLayout layout;

		if (convertView == null) {
			layout = (ConstraintLayout) inflater.inflate(R.layout.product, parent, false);
		}
		else {
			layout = (ConstraintLayout) convertView;
		}

		final Product currentItem = (Product) getItem(position);

		ImageView productImg = layout.findViewById(R.id.product_image);
		if (currentItem.getImg() != null) {
			productImg.setImageBitmap(currentItem.getImg());
		}
		else if (currentItem.getImgId() != 0) {
			productImg.setImageResource(currentItem.getImgId());
		}

		((TextView) layout.findViewById(R.id.product_info_name)).setText(
				order.getProductCount(currentItem) + " x " + currentItem.getName());
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
