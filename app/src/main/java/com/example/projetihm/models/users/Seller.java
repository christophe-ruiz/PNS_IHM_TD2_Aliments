package com.example.projetihm.models.users;

import android.graphics.Bitmap;
import android.os.Parcel;

import com.example.projetihm.models.Timetable;

/**
 * @author Gabriel
 */
public class Seller extends User {
	private String address;
	private Timetable morning;
	private Timetable afternoon;

	public Seller (String email, String pwd, String name, String phone, String address,
				   Timetable morning, Timetable afternoon, Bitmap photo) {
		super (email, pwd, name, phone, photo);
		this.address = address;
		this.morning = morning;
		this.afternoon = afternoon;
	}

	protected Seller (Parcel in) {
		super (in);

		address = in.readString();
		morning = in.readParcelable(Timetable.class.getClassLoader());
		afternoon = in.readParcelable(Timetable.class.getClassLoader());
	}

	public String getAddress() {
		return address;
	}

	public Timetable getMorning() {
		return morning;
	}

	public Timetable getAfternoon() {
		return afternoon;
	}

	@Override
	public String getFullName() {
		return getName();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);

		dest.writeString(address);
		dest.writeParcelable(morning, flags);
		dest.writeParcelable(afternoon, flags);
	}
}
