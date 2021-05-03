package com.example.projetihm.fragments.usercreation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetihm.CreateUserActivity;
import com.example.projetihm.R;
import com.example.projetihm.factories.UserFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserTypeSelectionFragment#build} factory method to
 * create an instance of this fragment.
 */
public class UserTypeSelectionFragment extends Fragment {
	private CreateUserActivity activity = null;

	public UserTypeSelectionFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment UserTypeSelectionFragment.
	 */
	public static UserTypeSelectionFragment build() {
		UserTypeSelectionFragment fragment = new UserTypeSelectionFragment();
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
		View view = inflater.inflate(R.layout.fragment_user_type_selection, container,
				false);

		if (getActivity() instanceof CreateUserActivity) {
			activity = (CreateUserActivity) getActivity();
		}

		view.findViewById(R.id.seller_btn).setOnClickListener(v -> {
			if (activity != null) {
				activity.setUserFactory(UserFactory.getFactoryFor(UserFactory.SELLER_TYPE),
						UserFactory.SELLER_TYPE);
			}
		});

		view.findViewById(R.id.consumer_btn).setOnClickListener(v -> {
			if (activity != null) {
				activity.setUserFactory(UserFactory.getFactoryFor(UserFactory.CONSUMER_TYPE),
						UserFactory.CONSUMER_TYPE);
			}
		});

		return view;
	}
}