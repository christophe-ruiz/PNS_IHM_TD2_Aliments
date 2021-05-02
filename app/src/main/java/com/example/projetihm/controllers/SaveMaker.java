package com.example.projetihm.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.projetihm.models.JsonConvertible;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Gabriel
 */
public class SaveMaker {

	public static String saveImageToInternalStorage(Bitmap img, Context context) {
		String pictureName = "co_user_photo.png";

		File file = new File(context.getFilesDir(), pictureName);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			img.compress(Bitmap.CompressFormat.PNG, 90, fos);
		} catch (FileNotFoundException e) {
			Log.d("Projet IHM", e.getMessage());
		}
		return file.getAbsolutePath();
	}

	public static void saveToInternalStorage(JsonConvertible data, String filename, Context context) {
		String val = data.toJsonString();

		try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
			if (val != null) {
				fos.write(val.getBytes());
			}
		} catch (IOException e) {
			Log.d("Projet IHM", e.getMessage());
		}
	}

	public static void saveArrayToInternalStorage(List<JsonConvertible> data,
												  String filename, Context context) {
		StringBuilder val = new StringBuilder("[");
		for (int i = 0; i < data.size() - 1; i++) {
			val.append(data.get(i)).append(",");
		}
		val.append(data.get(data.size() - 1)).append("]");

		try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
			fos.write(val.toString().getBytes());
		} catch (IOException e) {
			Log.d("Projet IHM", e.getMessage());
		}
	}

	public static String readFile (String filename, Context context) {
		try {
			FileInputStream fis = context.openFileInput(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			Log.d("Projet IHM", e.getMessage());
			return null;
		}
	}

	public static boolean removeFile(String filename, Context context) {
		File file = new File(context.getFilesDir(), filename);
		return file.delete();
	}
}
