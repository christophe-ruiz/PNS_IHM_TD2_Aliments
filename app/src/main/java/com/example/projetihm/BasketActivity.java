package com.example.projetihm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetihm.models.Basket;
import com.example.projetihm.models.Manager;
import com.example.projetihm.models.Product;
import com.example.projetihm.models.ProductAdapter;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
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
		basket.add(new Product(null, "Pomme Verte", "Espagne", 1.00, "Jolie pomme verte", false, false));
		basket.add(new Product(null, "Pomme Bleue", "France", 2.00, "Jolie pomme bleue", true, false));
		displayBasketContent();
		RecyclerView products = findViewById(R.id.basket_item_list);
		products.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				if (parent.getChildLayoutPosition(view) == 0)
					outRect.top = 10;
				outRect.bottom = 20;
			}
		});
		adapter = new ProductAdapter(new ArrayList<>(basket.getProducts().keySet()));
		products.setAdapter(adapter);
		products.setLayoutManager(new LinearLayoutManager(this));
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