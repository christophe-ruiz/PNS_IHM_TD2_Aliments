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
import com.example.projetihm.factories.users.SellerFactory;
import com.example.projetihm.models.Time;
import com.example.projetihm.models.Timetable;
import com.example.projetihm.models.users.Seller;
import com.example.projetihm.models.users.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellerFormFragment#build} factory method to
 * create an instance of this fragment.
 */
public class SellerFormFragment extends Fragment implements IPhotoManager {
	private static final String EMAIL = "email";
	private static final String PWD = "pwd";

	private String email;
	private String pwd;

	private Time morningStartTime = null;
	private Time morningEndTime = null;
	private Time afternoonStartTime = null;
	private Time afternoonEndTime = null;

	private CreateUserActivity activity = null;
	private EditProfileActivity editProfileActivity = null;

	private TextInputLayout nameTIL;
	private TextInputLayout phoneTIL;
	private TextInputLayout addressTIL;
	private TextInputLayout morningStartTIL;
	private TextInputLayout morningEndTIL;
	private TextInputLayout afternoonStartTIL;
	private TextInputLayout afternoonEndTIL;
	private ImageView photoView;

	public SellerFormFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param email New user email
	 * @param pwd New user password
	 * @return A new instance of fragment SellerFormFragment.
	 */
	public static SellerFormFragment build(String email, String pwd) {
		SellerFormFragment fragment = new SellerFormFragment();
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
		View view = inflater.inflate(R.layout.fragment_seller_form, container, false);

		assert getActivity() != null;
		if (getActivity() instanceof CreateUserActivity) {
			activity = (CreateUserActivity) getActivity();
		}
		else if (getActivity() instanceof EditProfileActivity) {
			editProfileActivity = (EditProfileActivity) getActivity();
		}

		nameTIL = view.findViewById(R.id.name_field);
		phoneTIL = view.findViewById(R.id.phone_field);
		addressTIL = view.findViewById(R.id.address_field);
		photoView = view.findViewById(R.id.if_profile_img);
		morningStartTIL = view.findViewById(R.id.morning_from_field);
		morningEndTIL = view.findViewById(R.id.morning_to_field);
		afternoonStartTIL = view.findViewById(R.id.afternoon_from_field);
		afternoonEndTIL = view.findViewById(R.id.afternoon_to_field);

		assert nameTIL.getEditText() != null && phoneTIL.getEditText() != null &&
				addressTIL.getEditText() != null && morningStartTIL.getEditText() != null &&
				morningEndTIL.getEditText() != null && afternoonStartTIL.getEditText() != null &&
				afternoonEndTIL.getEditText() != null;

		Bitmap photo = BitmapFactory.decodeResource(getActivity().getResources(),
				R.mipmap.avatar_person);
		photoView.setImageBitmap(photo);

		/*	Morning start time	*/
		view.findViewById(R.id.morning_start_time).setOnClickListener(v -> {
			MaterialTimePicker materialTimePicker = buildMaterialTimePicker(8, 0,
					morningStartTIL, morningStartTime);

			materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
				morningStartTime = new Time(materialTimePicker.getHour(),
						materialTimePicker.getMinute());
				morningStartTIL.getEditText().setText(morningStartTime.toString());
				if (editProfileActivity != null) {
					validate();
				}
			});

			materialTimePicker.show(activity.getSupportFragmentManager(), "tag");
		});

		/*	Morning end time	*/
		view.findViewById(R.id.morning_end_time).setOnClickListener(v -> {
			MaterialTimePicker materialTimePicker = buildMaterialTimePicker(12, 0,
					morningEndTIL, morningEndTime);

			materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
				morningEndTime = new Time(materialTimePicker.getHour(),
						materialTimePicker.getMinute());
				morningEndTIL.getEditText().setText(morningEndTime.toString());
				if (editProfileActivity != null) {
					validate();
				}
			});

			materialTimePicker.show(activity.getSupportFragmentManager(), "tag");
		});

		/*	Afternoon start time	*/
		view.findViewById(R.id.afternoon_start_time).setOnClickListener(v -> {
			MaterialTimePicker materialTimePicker = buildMaterialTimePicker(13, 30,
					afternoonStartTIL, afternoonStartTime);

			materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
				afternoonStartTime = new Time(materialTimePicker.getHour(),
						materialTimePicker.getMinute());
				afternoonStartTIL.getEditText().setText(afternoonStartTime.toString());
				if (editProfileActivity != null) {
					validate();
				}
			});

			materialTimePicker.show(activity.getSupportFragmentManager(), "tag");
		});

		/*	Morning end time	*/
		view.findViewById(R.id.afternoon_end_time).setOnClickListener(v -> {
			MaterialTimePicker materialTimePicker = buildMaterialTimePicker(17, 30,
					afternoonEndTIL, afternoonEndTime);

			materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
				afternoonEndTime = new Time(materialTimePicker.getHour(),
						materialTimePicker.getMinute());
				afternoonEndTIL.getEditText().setText(afternoonEndTime.toString());
				if (editProfileActivity != null) {
					validate();
				}
			});

			materialTimePicker.show(activity.getSupportFragmentManager(), "tag");
		});

		view.findViewById(R.id.validate_btn).setOnClickListener(v -> validate());
		if (editProfileActivity != null) {
			view.findViewById(R.id.validate_btn).setVisibility(View.INVISIBLE);
			/*	Set data	*/
			Seller seller = (Seller) editProfileActivity.getCurrentUser();
			nameTIL.getEditText().setText(seller.getName());
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
			phoneTIL.getEditText().setText(seller.getPhone());
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
			addressTIL.getEditText().setText(seller.getAddress());
			addressTIL.getEditText().addTextChangedListener(new TextWatcher() {
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
			photoView.setImageBitmap(seller.getPhoto());
			morningStartTime = seller.getMorning().getStart();
			morningEndTime = seller.getMorning().getEnd();
			afternoonStartTime = seller.getAfternoon().getStart();
			afternoonEndTime = seller.getAfternoon().getEnd();
			morningStartTIL.getEditText().setText(morningStartTime.toString());
			morningEndTIL.getEditText().setText(morningEndTime.toString());
			afternoonStartTIL.getEditText().setText(afternoonStartTime.toString());
			afternoonStartTIL.getEditText().setText(afternoonStartTime.toString());
		}

		view.findViewById(R.id.btn_choose_img).setOnClickListener(v -> {
			Intent intent = new Intent(getContext(), PickPhotoActivity.class);
			startActivityForResult(intent, PickPhotoActivity.SELECT_PHOTO_RESULT_CODE);
		});

		return view;
	}

	private MaterialTimePicker buildMaterialTimePicker (int hour, int minute,
														TextInputLayout layout, Time val) {
		MaterialTimePicker materialTimePicker =  new MaterialTimePicker.Builder()
				.setTimeFormat(TimeFormat.CLOCK_12H)
				.setHour(hour)
				.setMinute(minute)
				.setTitleText(getString(R.string.time_picker_title))
				.build();

		materialTimePicker.addOnNegativeButtonClickListener(v1 -> {
				if (val == null)
					layout.setError(getString(R.string.required_field)); });
		materialTimePicker.addOnCancelListener(v1 -> {
			if (val == null)
				layout.setError(getString(R.string.required_field)); });

		return materialTimePicker;
	}

	private boolean isMissingInfo () {
		assert nameTIL.getEditText() != null && phoneTIL.getEditText() != null &&
				addressTIL.getEditText() != null;
		return nameTIL.getEditText().getText().toString().isEmpty() ||
				phoneTIL.getEditText().getText().toString().isEmpty() ||
				addressTIL.getEditText().getText().toString().isEmpty() ||
				morningStartTime == null || morningEndTime == null ||
				afternoonStartTime == null || afternoonEndTime == null;
	}

	private void validate() {
		assert nameTIL.getEditText() != null && phoneTIL.getEditText() != null &&
				addressTIL.getEditText() != null && morningStartTIL.getEditText() != null &&
				morningEndTIL.getEditText() != null && afternoonStartTIL.getEditText() != null &&
				afternoonEndTIL.getEditText() != null;
		if (isMissingInfo()) {
			nameTIL.setError(getString(R.string.required_field));
			phoneTIL.setError(getString(R.string.required_field));
			addressTIL.setError(getString(R.string.required_field));
			morningStartTIL.setError(getString(R.string.required_field));
			morningEndTIL.setError(getString(R.string.required_field));
			afternoonStartTIL.setError(getString(R.string.required_field));
			afternoonEndTIL.setError(getString(R.string.required_field));
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
		data.put(SellerFactory.NAME, nameTIL.getEditText().getText().toString());
		data.put(UserFactory.PHONE, phoneTIL.getEditText().getText().toString());
		data.put(SellerFactory.ADDRESS, addressTIL.getEditText().getText().toString());
		data.put(SellerFactory.MORNING, new Timetable(morningStartTime, morningEndTime));
		data.put(SellerFactory.AFTERNOON, new Timetable(afternoonStartTime, afternoonEndTime));
		Bitmap photo = ((BitmapDrawable) photoView.getDrawable()).getBitmap();
		data.put(UserFactory.PHOTO, photo);

		return factory.build(data);
	}
}