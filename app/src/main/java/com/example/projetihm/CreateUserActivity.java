package com.example.projetihm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.fragments.usercreation.ConsumerFormFragment;
import com.example.projetihm.fragments.usercreation.IPhotoManager;
import com.example.projetihm.fragments.usercreation.SellerFormFragment;
import com.example.projetihm.fragments.usercreation.UserGlobalFormFragment;
import com.example.projetihm.fragments.usercreation.UserTypeSelectionFragment;
import com.example.projetihm.models.users.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CreateUserActivity extends AppCompatActivity {
	private UserFactory userFactory;
	private String userType = null;

	private IPhotoManager photoManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_user);

		getSupportActionBar().setTitle(R.string.create_user_title);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportFragmentManager().beginTransaction().add(R.id.fragment,
				UserTypeSelectionFragment.build()).commit();
	}

	@Override
	public boolean onSupportNavigateUp() {
		new MaterialAlertDialogBuilder(this)
				.setTitle(R.string.cancel_dialog_title)
				.setMessage(R.string.cancel_account_creation_dialog_msg)
				.setPositiveButton(R.string.edit_profile_dialog_positive_btn,
						(dialog, which) -> finish())
				.setNegativeButton(R.string.edit_profile_dialog_negative_btn,
						(dialog, which) -> {})
				.show();
		return super.onSupportNavigateUp();
	}

	public void setUserFactory(UserFactory userFactory, String type) {
		this.userFactory = userFactory;
		userType = type;
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
				UserGlobalFormFragment.build()).commit();
	}

	public UserFactory getUserFactory() {
		return userFactory;
	}

	public void nextFormPart (String email, String pwd) {
		if (userType.equals(UserFactory.CONSUMER_TYPE)) {
			photoManager = ConsumerFormFragment.build(email, pwd);
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
					(Fragment) photoManager).commit();
		}
		else if (userType.equals(UserFactory.SELLER_TYPE)) {
			photoManager = SellerFormFragment.build(email, pwd);
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
					(Fragment) photoManager).commit();
		}
	}

	public void createUser (User user) {
		Intent data = new Intent();
		data.putExtra(User.USER_PARCELABLE_NAME, user);
		setResult(User.USER_REQUEST_CODE, data);
		finish();
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
}