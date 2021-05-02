package com.example.projetihm.fragments.usercreation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetihm.CreateUserActivity;
import com.example.projetihm.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserGlobalFormFragment#build} factory method to
 * create an instance of this fragment.
 */
public class UserGlobalFormFragment extends Fragment {
	private View view;
	private TextInputLayout emailTIL;
	private TextInputLayout firstPasswordTIL;
	private TextInputLayout secondPasswordTIL;

	public UserGlobalFormFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment UserGlobalFormFragment.
	 */
	public static UserGlobalFormFragment build() {
		UserGlobalFormFragment fragment = new UserGlobalFormFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view =  inflater.inflate(R.layout.fragment_user_global_form, container, false);

		view.findViewById(R.id.next_btn).setOnClickListener(v -> next());

		emailTIL = view.findViewById(R.id.email_field);
		firstPasswordTIL = view.findViewById(R.id.first_pwd_field);
		secondPasswordTIL = view.findViewById(R.id.second_pwd_field);

		return view;
	}

	private boolean isMissingInfo() {
		if (emailTIL.getEditText() == null || firstPasswordTIL.getEditText() == null ||
			secondPasswordTIL.getEditText() == null) {
			return false;
		}
		return emailTIL.getEditText().getText().toString().isEmpty() ||
				firstPasswordTIL.getEditText().getText().toString().isEmpty() ||
				secondPasswordTIL.getEditText().getText().toString().isEmpty();
	}

	private boolean check() {
		assert firstPasswordTIL.getEditText() != null && secondPasswordTIL.getEditText() != null;
		return firstPasswordTIL.getEditText().getText().toString().equals(
				secondPasswordTIL.getEditText().getText().toString());
	}

	private void next() {
		if (isMissingInfo()) {
			emailTIL.setError(getString(R.string.required_field));
			firstPasswordTIL.setError(getString(R.string.required_field));
			secondPasswordTIL.setError(getString(R.string.required_field));
		}
		else if (!check()) {
			emailTIL.setError("");
			String errorMsg = "Les mots de passe ne sont pas les mêmes";
			firstPasswordTIL.setError(errorMsg);
			secondPasswordTIL.setError(errorMsg);
		}
		else {
			assert emailTIL.getEditText() != null && firstPasswordTIL.getEditText() != null;
			CreateUserActivity activity;
			if (getActivity() instanceof  CreateUserActivity) {
				activity = (CreateUserActivity) getActivity();
				activity.nextFormPart(emailTIL.getEditText().getText().toString(),
						firstPasswordTIL.getEditText().getText().toString());
			}
		}
	}
}