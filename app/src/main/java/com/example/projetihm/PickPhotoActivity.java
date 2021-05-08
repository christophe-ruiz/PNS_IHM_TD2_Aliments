package com.example.projetihm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PickPhotoActivity extends AppCompatActivity {
	public static final int SELECT_PHOTO_RESULT_CODE = 0;
	public static final String PHOTO_PARCELABLE_NAME = "photo";
	private static final int PICK_PHOTO_REQUEST_CODE = 1;
	private static final int CAMERA_PERM_REQUEST_CODE = 2;
	private static final int CHOOSE_FROM_GALLERY_REQUEST_CODE = 3;
	private static final int READ_STORAGE_PERM_REQUEST_CODE = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_photo);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		findViewById(R.id.pick_photo_btn).setOnClickListener(v -> pickPhoto());
		findViewById(R.id.choose_from_gallery_btn).setOnClickListener(v -> chooseFromGallery());
	}

	@Override
	public boolean onSupportNavigateUp() {
		finish();
		return super.onSupportNavigateUp();
	}

	/**
	 * Use the camera to pick a photo
	 */
	private void pickPhoto () {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[] { Manifest.permission.CAMERA }, CAMERA_PERM_REQUEST_CODE);
		}
		else {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, PICK_PHOTO_REQUEST_CODE);
		}
	}

	private void chooseFromGallery () {
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
					READ_STORAGE_PERM_REQUEST_CODE);
		}
		else {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, CHOOSE_FROM_GALLERY_REQUEST_CODE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_PHOTO_REQUEST_CODE) {
			if (data == null || data.getExtras() == null) {
				Toast.makeText(this, R.string.edit_profile_toast_error, Toast.LENGTH_SHORT)
						.show();
				return;
			}

			Bitmap photo = (Bitmap) data.getExtras().get("data");
			Intent intent = new Intent();
			intent.putExtra(PHOTO_PARCELABLE_NAME, photo);
			setResult(SELECT_PHOTO_RESULT_CODE, intent);
			finish();
		}
		else if (requestCode == CHOOSE_FROM_GALLERY_REQUEST_CODE) {
			if (data == null || data.getExtras() == null) {
				Toast.makeText(this, R.string.edit_profile_toast_error, Toast.LENGTH_SHORT)
						.show();
				return;
			}

			Bitmap photo;
			Uri photoUri = data.getData();
			final InputStream imageStream;
			try {
				imageStream = getContentResolver().openInputStream(photoUri);
				photo = BitmapFactory.decodeStream(imageStream);
				Intent intent = new Intent();
				intent.putExtra(PHOTO_PARCELABLE_NAME, photo);
				setResult(SELECT_PHOTO_RESULT_CODE, intent);
				finish();
			} catch (FileNotFoundException e) {
				Log.d("Projet IHM", e.getMessage());
				Toast.makeText(this, R.string.edit_profile_toast_error, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CAMERA_PERM_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				pickPhoto();
			}
		}
		else if (requestCode == READ_STORAGE_PERM_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				chooseFromGallery();
			}
		}
	}
}