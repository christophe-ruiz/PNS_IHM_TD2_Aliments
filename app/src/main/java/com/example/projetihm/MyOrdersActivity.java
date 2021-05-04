package com.example.projetihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.projetihm.adapaters.MyOrdersAdapter;
import com.example.projetihm.adapaters.OrdersListener;
import com.example.projetihm.controllers.Controller;
import com.example.projetihm.models.Order;

public class MyOrdersActivity extends AppCompatActivity implements OrdersListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_orders);

		getSupportActionBar().setTitle(R.string.my_orders_activity_title);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Controller ctrl = Controller.getInstance();

		GridView orderList = findViewById(R.id.my_order_list);
		MyOrdersAdapter adapter = new MyOrdersAdapter(this, ctrl.getOrders());
		adapter.setListener(this);
		orderList.setAdapter(adapter);
	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return super.onSupportNavigateUp();
	}

	@Override
	public void onClick(Order order) {
		Intent intent = new Intent(this, OrderActivity.class);
		intent.putExtra(OrderActivity.ORDER_ID_PARCEL_KEY, order.getId());
		startActivity(intent);
	}
}