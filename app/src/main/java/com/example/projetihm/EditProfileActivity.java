package com.example.projetihm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projetihm.controllers.Controller;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

public class EditProfileActivity extends AppCompatActivity {
	private Controller ctrl;
	private Bitmap photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ctrl = Controller.getInstance();

		TextInputLayout tfEditName = findViewById(R.id.tf_profile_name);
		tfEditName.getEditText().setText(ctrl.getUserConnected().getName());
		TextInputLayout tfEditFirstName = findViewById(R.id.tf_profile_first_name);
		tfEditFirstName.getEditText().setText(ctrl.getUserConnected().getFirstName());

		photo = ctrl.getUserConnected().getPhoto();
		((ImageView) findViewById(R.id.if_profile_img)).setImageBitmap(photo);

		findViewById(R.id.if_profile_img).setOnClickListener(v -> pickPicture());
		findViewById(R.id.btn_choose_img).setOnClickListener(v -> pickPicture());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.validate_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.item_validate) {
			check();
			finish();
			return true;
		}
		else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onSupportNavigateUp() {
		if (isDataChanged()) {
			new MaterialAlertDialogBuilder(this)
					.setTitle("Annuler")
					.setMessage("Supprimer les modifications ?")
					.setPositiveButton(R.string.edit_profile_dialog_positive_btn,
							(dialog, which) -> finish())
					.setNegativeButton(R.string.edit_profile_dialog_negative_btn,
							(dialog, which) -> {})
					.show();
		}
		else {
			finish();
		}
		return super.onSupportNavigateUp();
	}

	private void pickPicture () {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, 0);
		}
		else {
			takePicture();
		}
	}

	private void takePicture() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1);
	}

	private void choosePhoto () {
		Intent intent = new Intent();
		startActivityForResult(intent, 1);
	}

	private void check() {
		TextInputLayout tfEditName = findViewById(R.id.tf_profile_name);
		ctrl.getUserConnected().setName(tfEditName.getEditText().getText().toString());
		TextInputLayout tfEditFirstName = findViewById(R.id.tf_profile_first_name);
		ctrl.getUserConnected().setFirstName(tfEditFirstName.getEditText().getText().toString());
		ctrl.getUserConnected().setPhoto(photo);
	}

	private boolean isDataChanged () {
		TextInputLayout tfEditName = findViewById(R.id.tf_profile_name);
		TextInputLayout tfEditFirstName = findViewById(R.id.tf_profile_first_name);

		boolean res = false;

		res |= !ctrl.getUserConnected().getName().equals(
				tfEditName.getEditText().getText().toString());

		res |= !ctrl.getUserConnected().getFirstName().equals(
				tfEditFirstName.getEditText().getText().toString());

		res |= !photo.equals(ctrl.getUserConnected().getPhoto());

		return res;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (data == null || data.getExtras() == null) {
				Toast.makeText(this, R.string.edit_profile_toast_error, Toast.LENGTH_SHORT)
						.show();
				return;
			}

			photo = (Bitmap) data.getExtras().get("data");
			((ImageView) findViewById(R.id.if_profile_img)).setImageBitmap(photo);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 0) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				takePicture();
			}
		}
	}
}