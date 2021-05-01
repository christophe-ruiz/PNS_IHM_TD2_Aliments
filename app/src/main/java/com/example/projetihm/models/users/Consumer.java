package com.example.projetihm.models.users;

import android.graphics.Bitmap;
import android.os.Parcel;

/**
 * @author Gabriel
 */
public class Consumer extends User {
	private String firstName;

	public Consumer(String email, String mdp, String name, String firstName, String phone,
					Bitmap photo) {
		super(email, mdp, name, phone, photo);
		this.firstName = firstName;
	}

	protected Consumer(Parcel in) {
		super (in);

		firstName = in.readString();
	}

	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getFullName() {
		return getFirstName() + " " + getName();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);

		dest.writeString(firstName);
	}
}
