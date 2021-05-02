package com.example.projetihm.factories.users;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.models.users.Consumer;
import com.example.projetihm.models.users.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
		Bitmap photo = null;

		try {
			File file = new File(object.getString(PHOTO));
			photo = BitmapFactory.decodeStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			Log.d("Projet IHM", e.getMessage());
		}

		return new Consumer(email, mdp, name, firstName, phone, photo);
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
