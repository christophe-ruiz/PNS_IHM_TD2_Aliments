package com.example.projetihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.projetihm.controllers.Controller;
import com.example.projetihm.controllers.SaveMaker;
import com.example.projetihm.models.users.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

		findViewById(R.id.disableAccount).setOnClickListener(v ->
				new MaterialAlertDialogBuilder(this)
					.setTitle(R.string.dialog_disable_account_title)
					.setMessage(R.string.dialog_disable_account_msg)
					.setPositiveButton(R.string.yes, (dialog, which) -> {
						controller.setUserConnected((User) null);
						SaveMaker.removeFile(LoginActivity.SAVE_CO_USER_FILE_NAME, this);
						Intent intent = new Intent(this, LoginActivity.class);
						startActivity(intent);
					})
					.setNegativeButton(R.string.no, ((dialog, which) -> {}))
					.show());
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
		if (controller.getUserConnected() == null)
			return;
		String title = controller.getUserConnected().getFullName();
		getSupportActionBar().setTitle(title);
		((ImageView) findViewById(R.id.img_user)).setImageBitmap(
				controller.getUserConnected().getPhoto());
	}
}