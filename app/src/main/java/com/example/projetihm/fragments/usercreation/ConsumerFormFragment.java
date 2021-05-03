package com.example.projetihm.fragments.usercreation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.projetihm.CreateUserActivity;
import com.example.projetihm.EditProfileActivity;
import com.example.projetihm.PickPhotoActivity;
import com.example.projetihm.R;
import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.factories.users.ConsumerFactory;
import com.example.projetihm.models.users.Consumer;
import com.example.projetihm.models.users.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsumerFormFragment#build} factory method to
 * create an instance of this fragment.
 */
public class ConsumerFormFragment extends Fragment implements IPhotoManager {
	private static final String EMAIL = "email";
	private static final String PWD = "pwd";

	private String email;
	private String pwd;

	private CreateUserActivity activity = null;
	private EditProfileActivity editProfileActivity = null;

	private TextInputLayout nameTIL;
	private TextInputLayout firstNameTIL;
	private TextInputLayout phoneTIL;
	private ImageView photoView;

	public ConsumerFormFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param email New user email
	 * @param pwd New user password
	 * @return A new instance of fragment ConsumerFormFragment.
	 */
	public static ConsumerFormFragment build(String email, String pwd) {
		ConsumerFormFragment fragment = new ConsumerFormFragment();
		Bundle args = new Bundle();
		args.putString(EMAIL, email);
		args.putString(PWD, pwd);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			email = getArguments().getString(EMAIL);
			pwd = getArguments().getString(PWD);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_consumer_form, container, false);
		assert getActivity() != null;
		if (getActivity() instanceof CreateUserActivity) {
			activity = (CreateUserActivity) getActivity();
		}
		else if (getActivity() instanceof EditProfileActivity) {
			editProfileActivity = (EditProfileActivity) getActivity();
		}

		nameTIL = view.findViewById(R.id.name_field);
		firstNameTIL = view.findViewById(R.id.first_name_field);
		phoneTIL = view.findViewById(R.id.phone_field);
		photoView = view.findViewById(R.id.if_profile_img);

		Bitmap photo = BitmapFactory.decodeResource(getActivity().getResources(),
				R.mipmap.avatar_person);
		photoView.setImageBitmap(photo);

		view.findViewById(R.id.validate_btn).setOnClickListener(v -> validate());

		view.findViewById(R.id.btn_choose_img).setOnClickListener(v -> {
			Intent intent = new Intent(getContext(), PickPhotoActivity.class);
			startActivityForResult(intent, PickPhotoActivity.SELECT_PHOTO_RESULT_CODE);
		});
		if (editProfileActivity != null) {
			view.findViewById(R.id.validate_btn).setVisibility(View.INVISIBLE);

			Consumer user = (Consumer) editProfileActivity.getCurrentUser();
			nameTIL.getEditText().setText(user.getName());
			nameTIL.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					if (editProfileActivity != null)
						validate();
				}
			});
			firstNameTIL.getEditText().setText(user.getFirstName());
			firstNameTIL.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					if (editProfileActivity != null)
						validate();
				}
			});
			phoneTIL.getEditText().setText(user.getPhone());
			phoneTIL.getEditText().addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					if (editProfileActivity != null)
						validate();
				}
			});
			photoView.setImageBitmap(user.getPhoto());
		}

		return view;
	}

	private boolean isMissingInfo() {
		assert nameTIL.getEditText() != null &&
				firstNameTIL.getEditText() != null;

		return nameTIL.getEditText().getText().toString().isEmpty() &&
				firstNameTIL.getEditText().getText().toString().isEmpty();
	}

	private void validate () {
		assert nameTIL.getEditText() != null &&
				firstNameTIL.getEditText() != null &&
				phoneTIL.getEditText() != null;
		if (isMissingInfo()) {
			nameTIL.setError(getString(R.string.required_field));
			firstNameTIL.setError(getString(R.string.required_field));
		}
		else {

			User user = getData();
			if (user != null && activity != null) {
				activity.createUser(user);
			}
			else if (user != null && editProfileActivity != null) {
				editProfileActivity.editUser(user);
			}
		}
	}

	@Override
	public void managePhoto(Bitmap photo) {
		photoView.setImageBitmap(photo);
		if (editProfileActivity != null) {
			validate();
		}
	}

	@Override
	public User getData() {
		UserFactory factory;
		if (activity != null)
			factory = activity.getUserFactory();
		else
			factory = editProfileActivity.getUserFactory();
		Map<String, Object> data = new HashMap<>();
		data.put(UserFactory.EMAIL, email);
		data.put(UserFactory.PASSWORD, pwd);
		data.put(ConsumerFactory.NAME, nameTIL.getEditText().getText().toString());
		data.put(ConsumerFactory.FIRST_NAME, firstNameTIL.getEditText().getText().toString());
		data.put(UserFactory.PHONE, phoneTIL.getEditText().getText().toString());
		Bitmap photo = ((BitmapDrawable) photoView.getDrawable()).getBitmap();
		data.put(UserFactory.PHOTO, photo);

		return factory.build(data);
	}
}