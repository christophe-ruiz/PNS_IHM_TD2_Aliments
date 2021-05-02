package com.example.projetihm.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Gabriel
 */
public class Timetable implements Parcelable, JsonConvertible {
	private final Time start;
	private final Time end;

	public Timetable (Time start, Time end) {
		this.start = start;
		this.end = end;
	}

	protected Timetable(Parcel in) {
		start = in.readParcelable(Time.class.getClassLoader());
		end = in.readParcelable(Time.class.getClassLoader());
	}

	public static final Creator<Timetable> CREATOR = new Creator<Timetable>() {
		@Override
		public Timetable createFromParcel(Parcel in) {
			return new Timetable(in);
		}

		@Override
		public Timetable[] newArray(int size) {
			return new Timetable[size];
		}
	};

	public Time getStart() {
		return start;
	}

	public Time getEnd() {
		return end;
	}

	public static Timetable buildFromJson (JSONObject object) throws JSONException {
		Time start = Time.buildFromJSON(object.getJSONObject("start"));
		Time end = Time.buildFromJSON(object.getJSONObject("end"));

		return new Timetable(start, end);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(start, flags);
		dest.writeParcelable(end, flags);
	}

	@Override
	public String toJsonString() {
		String res = "{";
		res += "\"start\": "  + start.toJsonString() + ",";
		res += "\"end\": "  + end.toJsonString() + "}";
		return res;
	}
}
