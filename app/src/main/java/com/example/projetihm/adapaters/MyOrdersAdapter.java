package com.example.projetihm.adapaters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.projetihm.R;
import com.example.projetihm.models.Order;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

/**
 * @author Gabriel
 */
public class MyOrdersAdapter extends BaseAdapter {
	private final LayoutInflater inflater;
	private final List<Order> orders;

	private OrdersListener listener;

	public MyOrdersAdapter (Context context, List<Order> orders) {
		this.inflater = LayoutInflater.from(context);
		this.orders = orders;
	}

	public void setListener(OrdersListener listener) {
		this.listener = listener;
	}

	@Override
	public int getCount() {
		return orders.size();
	}

	@Override
	public Object getItem(int position) {
		return orders.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("SetTextI18n")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MaterialCardView view;
		if (convertView == null) {
			view = (MaterialCardView) inflater.inflate(R.layout.order_quick_view_layout,
					parent, false);
		} else {
			view = (MaterialCardView) convertView;
		}

		final Order currentItem = (Order) getItem(position);

		((MaterialTextView) view.findViewById(R.id.order_id))
				.setText("N° " + currentItem.getId());

		((MaterialTextView) view.findViewById(R.id.order_price))
				.setText(currentItem.getTotalPrice() + " €");

		((MaterialTextView) view.findViewById(R.id.order_status))
				.setText(getStatusTranslation(currentItem.getStatus()));

		view.setOnClickListener(v -> {
			if (listener != null) {
				listener.onClick(currentItem);
			}
		});

		return view;
	}

	private String getStatusTranslation (Order.Status status) {
		switch (status) {
			case RUNNING:
				return "en cours";
			case RECEIVED:
				return "reçue";
			case READY:
				return "prête";
			case DELIVERED:
				return "livrée";
			default:
				return "";
		}
	}
}
