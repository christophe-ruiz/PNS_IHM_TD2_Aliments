package com.example.projetihm.factories.users;

import android.graphics.Bitmap;

import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.models.users.Consumer;
import com.example.projetihm.models.users.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author Gabriel
 */
public class ConsumerFactory implements UserFactory {
	public static final String NAME = "last_name";
	public static final String FIRST_NAME = "first_name";

	@Override
	public User build(JSONObject object) throws JSONException {
		String email = object.getString(EMAIL);
		String mdp = object.getString(PASSWORD);
		String name = object.getString(NAME);
		String firstName = object.getString(FIRST_NAME);
		String phone = object.getString(PHONE);

		return new Consumer(email, mdp, name, firstName, phone, null);
	}

	@Override
	public User build(Map<String, Object> data) {
		String email = (String) data.get(EMAIL);
		String mdp = (String) data.get(PASSWORD);
		String name = (String) data.get(NAME);
		String firstName = (String) data.get(FIRST_NAME);
		String phone = (String) data.get(PHONE);
		Bitmap photo = (Bitmap) data.get(PHOTO);

		return new Consumer(email, mdp, name, firstName, phone, photo);
	}
}
