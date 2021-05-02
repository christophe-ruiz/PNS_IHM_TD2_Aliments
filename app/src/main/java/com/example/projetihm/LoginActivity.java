package com.example.projetihm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projetihm.controllers.Controller;
import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.models.users.User;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
	private List<User> users;
	private Controller ctrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		users = buildUsers();
		ctrl = Controller.getInstance();

		findViewById(R.id.validate_btn).setOnClickListener(v -> {
			User user = validateConnection();
			if (user != null) {
				// End this activity
				user.setPhoto(BitmapFactory.decodeResource(getResources(),
						R.mipmap.avatar_person));
				ctrl.setUserConnected(user);
				finish();
			}
			else {
				Toast.makeText(LoginActivity.this, getText(R.string.login_error),
						Toast.LENGTH_SHORT).show();
			}
		});

		findViewById(R.id.create_account_btn).setOnClickListener(v -> {
			Intent intent = new Intent(LoginActivity.this, CreateUserActivity.class);
			startActivityForResult(intent, User.USER_REQUEST_CODE);
		});
	}

	private List<User> buildUsers () {
		List<User> users = new ArrayList<>();

		try {
			JSONArray array = new JSONArray(readJsonFile(this));

			JSONObject object;
			String type;
			UserFactory factory;
			for (int i = 0; i < array.length(); i++) {
				object = array.getJSONObject(i);
				type = object.getString(UserFactory.TYPE);
				factory = UserFactory.getFactoryFor(type);
				if (factory != null) {
					users.add(factory.build(object));
				}
			}
		}
		catch (JSONException ignored) {
			//
		}

		return users;
	}

	private static String readJsonFile(Context context) {
		String json = "";

		try {
			InputStream is = context.getAssets().open("users.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			int length = is.read(buffer);
			is.close();
			json = new String(buffer, 0, length, StandardCharsets.UTF_8);
		} catch (IOException ignored) {
		}

		return json;
	}

	private User validateConnection() {
		TextInputLayout emailTIL = findViewById(R.id.id_tf);
		TextInputLayout mdpTIL = findViewById(R.id.password_tf);
		if (emailTIL.getEditText() == null || mdpTIL.getEditText() == null)
			return null;

		String email = emailTIL.getEditText().getText().toString();
		String password = mdpTIL.getEditText().getText().toString();

		return users.stream()
				.filter(user -> email.equalsIgnoreCase(user.getEmail())
						&& password.equals(user.getPwd()))
				.findAny().orElse(null);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == User.USER_REQUEST_CODE) {
			if (data == null || data.getExtras() == null) {
				return;
			}

			User user = (User) data.getExtras().get(User.USER_PARCELABLE_NAME);
			ctrl.setUserConnected(user);
			finish();
		}
	}
}