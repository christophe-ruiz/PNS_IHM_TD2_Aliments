package com.example.projetihm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projetihm.adapaters.ProductAdapter;
import com.example.projetihm.controllers.Controller;
import com.example.projetihm.models.Order;
import com.example.projetihm.models.users.Consumer;
import com.example.projetihm.models.users.Seller;

public class OrderActivity extends AppCompatActivity {
	public static final String ORDER_ID_PARCEL_KEY = "order_id";
	private Order order;
	private ProductAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		assert intent.getExtras() != null;
		long orderId = intent.getLongExtra(ORDER_ID_PARCEL_KEY, 0);

		getSupportActionBar().setTitle("Commande N° " + orderId);

		Controller ctrl = Controller.getInstance();
		order = ctrl.getOrderById(orderId);

		adapter = new ProductAdapter(this, order);
		ListView productList = findViewById(R.id.product_list);
		productList.setAdapter(adapter);

		display();
		if(Controller.getInstance().getUserConnected().getClass()== Seller.class){
			findViewById(R.id.shareButton).setVisibility(View.INVISIBLE);
			findViewById(R.id.visibleOrderButton).setOnClickListener(click->{
				this.order.setStatus(Order.Status.READY);
			});
		}else{
			findViewById(R.id.visibleOrderButton).setVisibility(View.INVISIBLE);
			findViewById(R.id.shareButton).setOnClickListener(click->{
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, order.toString());
				sendIntent.setType("text/plain");
				Intent shareIntent = Intent.createChooser(sendIntent, null);
				startActivity(shareIntent);
			});
		}


	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return super.onSupportNavigateUp();
	}

	@SuppressLint("SetTextI18n")
	private void display() {
		if (order != null) {
			((TextView) findViewById(R.id.order_total_price)).setText(order.getTotalPrice() + " €");
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
		}
	}
}