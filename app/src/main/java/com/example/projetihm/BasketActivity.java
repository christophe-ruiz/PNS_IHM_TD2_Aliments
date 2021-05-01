package com.example.projetihm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.projetihm.models.Basket;

public class BasketActivity extends AppCompatActivity {
	private Basket basket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basket);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		basket = Basket.getInstance();
		displayBasketContent();
	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return super.onSupportNavigateUp();
	}

	private void displayBasketContent() {
		if (basket.isEmpty()) {
			findViewById(R.id.txt_empty_basket).setVisibility(View.VISIBLE);
			findViewById(R.id.btn_check_out_basket).setEnabled(false);
		}
		else {
			findViewById(R.id.txt_empty_basket).setVisibility(View.INVISIBLE);
			findViewById(R.id.btn_check_out_basket).setEnabled(true);
		}
	}
}