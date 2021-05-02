package com.example.projetihm.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.example.projetihm.models.JsonConvertible;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Gabriel
 */
public class SaveMaker {

	public static String saveImageToInternalStorage(Bitmap img, Context context) {
		String pictureName = "co_user_photo.png";
		/*ContextWrapper cw = new ContextWrapper(context);
		String directoryName = cw.getDir("imageDir", ContextWrapper.MODE_PRIVATE).getPath();

		ContentValues contentValues = new ContentValues();
		contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, pictureName);
		contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/*");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, directoryName);
		}
		contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

		context.getContentResolver()
				.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);*/

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
}
