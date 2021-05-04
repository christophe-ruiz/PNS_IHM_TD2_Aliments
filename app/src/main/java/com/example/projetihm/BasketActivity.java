package com.example.projetihm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projetihm.models.Basket;

import android.widget.ListView;
import com.example.projetihm.adapaters.ProductAdapter;

import java.util.Observable;
import java.util.Observer;

public class BasketActivity extends AppCompatActivity implements Observer {
	private Basket basket;
	private ProductAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basket);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		basket = Basket.getInstance();
		basket.addObserver(this);

		displayBasketContent();
		ListView products = findViewById(R.id.basket_item_list);
		adapter = new ProductAdapter(this, basket.toOrder());
		products.setAdapter(adapter);
	}

	@SuppressLint("SetTextI18n")
	@Override
	protected void onResume() {
		super.onResume();
		basket.calculateTotal();
		((TextView) findViewById(R.id.basket_total_value)).setText(String.valueOf(basket.getTotal()));
	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return super.onSupportNavigateUp();
	}

	private void displayBasketContent() {
		if (basket.isEmpty()) {
			findViewById(R.id.txt_empty_basket).setVisibility(View.VISIBLE);
			findViewById(R.id.basket_total_currency).setVisibility(View.INVISIBLE);
			findViewById(R.id.basket_total_value).setVisibility(View.INVISIBLE);
			findViewById(R.id.basket_total).setVisibility(View.INVISIBLE);
			findViewById(R.id.btn_check_out_basket).setEnabled(false);
		}
		else {
			findViewById(R.id.txt_empty_basket).setVisibility(View.INVISIBLE);
			findViewById(R.id.basket_total_currency).setVisibility(View.VISIBLE);
			findViewById(R.id.basket_total_value).setVisibility(View.VISIBLE);
			findViewById(R.id.basket_total).setVisibility(View.VISIBLE);
			findViewById(R.id.btn_check_out_basket).setEnabled(true);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}
}