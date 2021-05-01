package com.example.projetihm.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Gabriel
 */
public class Time implements Parcelable {
	private final int hour;
	private final int minute;

	public Time (int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}

	protected Time(Parcel in) {
		hour = in.readInt();
		minute = in.readInt();
	}

	public static final Creator<Time> CREATOR = new Creator<Time>() {
		@Override
		public Time createFromParcel(Parcel in) {
			return new Time(in);
		}

		@Override
		public Time[] newArray(int size) {
			return new Time[size];
		}
	};

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(hour);
		dest.writeInt(minute);
	}

	public static Time buildFromJSON (JSONObject object) throws JSONException {
		int hour = object.getInt("hour");
		int minute = object.getInt("minute");

		return new Time(hour, minute);
	}

	@Override
	public String toString() {
		return (hour < 10 ? "0" : "") + hour + " : " + (minute < 10 ? "0" : "") + minute;
	}
}
