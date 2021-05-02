package com.example.projetihm.models.users;

import android.graphics.Bitmap;
import android.os.Parcel;

import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.factories.users.SellerFactory;
import com.example.projetihm.models.Timetable;

/**
 * @author Gabriel
 */
public class Seller extends User {
	private final String address;
	private final Timetable morning;
	private final Timetable afternoon;

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

	@Override
	public String toJsonString() {
		String res = super.toJsonString();

		res += "\"" + UserFactory.TYPE + "\": \"" + UserFactory.SELLER_TYPE + "\",";
		res += "\"" + SellerFactory.NAME + "\": \"" + getName() + "\",";
		res += "\"" + SellerFactory.ADDRESS + "\": \"" + address + "\",";
		res += "\"" + SellerFactory.MORNING + "\": " + morning.toJsonString() + ",";
		res += "\"" + SellerFactory.AFTERNOON + "\": " + afternoon.toJsonString();

		res += "}";

		return res;
	}
}
