package com.example.projetihm.application;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.projetihm.controllers.Controller;

import java.util.Objects;

/**
 * @author Gabriel
 */
public class ProjectApplication extends Application {
	private static final String CHANNEL_ID = Notification.CATEGORY_MESSAGE;

	private void createNotificationChannel(String name, String description, int importance) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
			channel.setDescription(description);
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Controller.getInstance();
		createNotificationChannel("Message", "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
	}
}
