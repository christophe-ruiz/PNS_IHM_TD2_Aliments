package com.example.projetihm.factories;

import com.example.projetihm.factories.users.ConsumerFactory;
import com.example.projetihm.factories.users.SellerFactory;
import com.example.projetihm.models.users.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author Gabriel
 */
public interface UserFactory {
	String SELLER_TYPE = "seller";
	String CONSUMER_TYPE = "consumer";

	String EMAIL = "email";
	String PASSWORD = "pwd";
	String TYPE = "type";
	String PHONE = "phone";
	String PHOTO = "photo";

	User build(JSONObject object) throws JSONException;

	User build(Map<String, Object> data);

	static UserFactory getFactoryFor (String type) {
		switch (type) {
			case SELLER_TYPE:
				return new SellerFactory();
			case CONSUMER_TYPE:
				return new ConsumerFactory();
			default:
				return null;
		}
	}
}
