package com.example.projetihm.models.users;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetihm.R;

/**
 * @author Gabriel
 */
public abstract class User implements Parcelable {
	public static final int USER_REQUEST_CODE = 0;
	public static final String USER_PARCELABLE_NAME = "user";

	private String email;
	private String pwd;
	private String name;

	private String phone;

	private Bitmap photo;

	public User (String email, String pwd, String name, String phone, Bitmap photo) {
		this.email = email;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
		this.photo = photo;
	}

	protected User (Parcel in) {
		email = in.readString();
		pwd = in.readString();
		name = in.readString();

		phone = in.readString();
	}

	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return String.copyValueOf(pwd.toCharArray());
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public abstract String getFullName();

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.getClass().getName()); // this as type

		dest.writeString(email);
		dest.writeString(pwd);
		dest.writeString(name);
		dest.writeString(phone);
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			String type = in.readString();

			if (type.equalsIgnoreCase(Consumer.class.getName())) {
				return new Consumer(in);
			}
			else if (type.equalsIgnoreCase(Seller.class.getName())) {
				return new Seller(in);
			}
			return null;
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
