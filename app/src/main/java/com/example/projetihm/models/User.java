package com.example.projetihm.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetihm.R;

/**
 * @author Gabriel
 */
public class User {
	private String name;
	private String firstName;
	private Bitmap photo;

	public User () {}
	public User(String name, String firstName, Bitmap photo) {
		this.name = name;
		this.firstName = firstName;
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public String getFirstName() {
		return firstName;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}

	public static User mock (AppCompatActivity activity) {
		return new User("Joe", "Bobby",
				BitmapFactory.decodeResource(activity.getResources(), R.mipmap.avatar_person));
	}
}
