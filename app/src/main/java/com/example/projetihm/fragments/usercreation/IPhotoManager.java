package com.example.projetihm.fragments.usercreation;

import android.graphics.Bitmap;

import com.example.projetihm.models.users.User;

/**
 * @author Gabriel
 */
public interface IPhotoManager {
	void managePhoto (Bitmap photo);

	User getData();
}
