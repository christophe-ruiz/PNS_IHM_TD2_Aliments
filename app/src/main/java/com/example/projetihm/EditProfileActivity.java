package com.example.projetihm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.projetihm.controllers.Controller;
import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.fragments.usercreation.ConsumerFormFragment;
import com.example.projetihm.fragments.usercreation.IPhotoManager;
import com.example.projetihm.fragments.usercreation.SellerFormFragment;
import com.example.projetihm.models.users.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class EditProfileActivity extends AppCompatActivity {
	private Controller ctrl;
	private User edited = null;

	private IPhotoManager photoManager;
	private UserFactory userFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ctrl = Controller.getInstance();

		if (ctrl.isSellerConnected()) {
			userFactory = UserFactory.getFactoryFor(UserFactory.SELLER_TYPE);
			photoManager = SellerFormFragment.build(ctrl.getUserConnected().getEmail(),
					ctrl.getUserConnected().getPwd());
		}
		else {
			userFactory = UserFactory.getFactoryFor(UserFactory.CONSUMER_TYPE);
			photoManager = ConsumerFormFragment.build(ctrl.getUserConnected().getEmail(),
					ctrl.getUserConnected().getPwd());
		}

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment, (Fragment) photoManager)
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.validate_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.item_validate) {
			validateChanges();
			finish();
			return true;
		}
		else {
			return super.onOptionsItemSelected(item);
		}
	}

	private boolean isDataChanged() {
		return edited != null;
	}

	@Override
	public boolean onSupportNavigateUp() {
		if (isDataChanged()) {
			new MaterialAlertDialogBuilder(this)
					.setTitle(R.string.cancel_dialog_title)
					.setMessage("Supprimer les modifications ?")
					.setPositiveButton(R.string.edit_profile_dialog_positive_btn,
							(dialog, which) -> finish())
					.setNegativeButton(R.string.edit_profile_dialog_negative_btn,
							(dialog, which) -> {
							})
					.show();
		}
		else {
			finish();
		}
		return super.onSupportNavigateUp();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if (data == null || data.getExtras() == null) {
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == PickPhotoActivity.SELECT_PHOTO_RESULT_CODE) {
			Bitmap photo = (Bitmap) data.getExtras().get(PickPhotoActivity.PHOTO_PARCELABLE_NAME);
			if (photoManager != null)
				photoManager.managePhoto(photo);
		}
	}

	public UserFactory getUserFactory() {
		return userFactory;
	}

	public User getCurrentUser () {
		return ctrl.getUserConnected();
	}

	public void editUser(User user) {
		edited = user;
	}

	public void validateChanges() {
		editUser(photoManager.getData());
		ctrl.setUserConnected(edited);
	}
}