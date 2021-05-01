package com.example.projetihm.factories.users;

import android.graphics.Bitmap;

import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.models.Timetable;
import com.example.projetihm.models.users.Seller;
import com.example.projetihm.models.users.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author Gabriel
 */
public class SellerFactory implements UserFactory {
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String MORNING = "morning";
	public static final String AFTERNOON = "afternoon";


	@Override
	public User build(JSONObject object) throws JSONException {
		String email = object.getString(EMAIL);
		String mdp = object.getString(PASSWORD);
		String name = object.getString(NAME);
		String phone = object.getString(PHONE);
		String address = object.getString(ADDRESS);
		Timetable morning = Timetable.buildFromJson(object.getJSONObject(MORNING));
		Timetable afternoon = Timetable.buildFromJson(object.getJSONObject(AFTERNOON));

		return new Seller(email, mdp, name, phone, address, morning, afternoon, null);
	}

	@Override
	public User build(Map<String, Object> data) {
		String email = (String) data.get(EMAIL);
		String mdp = (String) data.get(PASSWORD);
		String name = (String) data.get(NAME);
		String phone = (String) data.get(PHONE);
		String address = (String) data.get(ADDRESS);
		Timetable morning = (Timetable) data.get(MORNING);
		Timetable afternoon = (Timetable) data.get(AFTERNOON);
		Bitmap photo = (Bitmap) data.get(PHOTO);

		return new Seller(email, mdp, name, phone, address, morning, afternoon, photo);
	}
}
