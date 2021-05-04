package com.example.projetihm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projetihm.search.FragmentSaisie;

public class SearchActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportFragmentManager().beginTransaction().add(R.id.valeur, new FragmentSaisie()).commit();

	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return super.onSupportNavigateUp();
	}
}