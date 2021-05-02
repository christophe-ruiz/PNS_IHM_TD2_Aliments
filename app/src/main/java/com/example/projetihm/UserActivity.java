package com.example.projetihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

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

		findViewById(R.id.btn_edit_profile).setOnClickListener(v -> {
			Intent intent = new Intent(UserActivity.this, EditProfileActivity.class);
			startActivity(intent);
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
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
		assert getSupportActionBar() != null;
		if (controller == null) {
			controller = Controller.getInstance();
		}
		String title = controller.getUserConnected().getFullName();
		getSupportActionBar().setTitle(title);
		((ImageView) findViewById(R.id.img_user)).setImageBitmap(
				controller.getUserConnected().getPhoto());
	}
}