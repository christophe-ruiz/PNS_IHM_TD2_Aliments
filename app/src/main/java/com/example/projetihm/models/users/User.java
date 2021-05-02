package com.example.projetihm.models.users;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.models.JsonConvertible;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriel
 */
public abstract class User implements Parcelable, JsonConvertible {
	public static final int USER_REQUEST_CODE = 0;
	public static final String USER_PARCELABLE_NAME = "user";
	private static final Map<String, Bitmap> photos = new HashMap<>();

	private final String email;
	private final String pwd;
	private final String name;

	private final String phone;

	private Bitmap photo;
	private String photoPath = "";

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

		photo = photos.remove(email);
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

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;

	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
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

		photos.put(email, photo);
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

	@Override
	public String toJsonString() {
		String res = "{";

		res += "\"" + UserFactory.EMAIL + "\": \"" + email + "\",";
		res += "\"" + UserFactory.PASSWORD + "\": \"" + pwd + "\",";
		res += "\"" + UserFactory.PHONE + "\": \"" + phone + "\",";
		res += "\"" + UserFactory.PHOTO + "\": \"" + photoPath + "\",";

		return res;
	}
}
