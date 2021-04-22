package com.example.projetihm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projetihm.controllers.Controller;

import java.util.Observable;
import java.util.Observer;

public class UserActivity extends AppCompatActivity implements Observer {
	private Controller controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		controller = Controller.getInstance();
		controller.addObserver(this);

		display();
	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return super.onSupportNavigateUp();
	}

	@Override
	public void update(Observable o, Object arg) {
		display();
	}

	private void display() {
		getSupportActionBar().setTitle("Bobby Joe");
	}
}