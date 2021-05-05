package com.example.projetihm.application;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.projetihm.MainActivity;
import com.example.projetihm.R;
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

	private static int notification_id = 0;
	public void makeNotification(String title, String message,
								  Class<? extends AppCompatActivity> activity) {
		// Create an explicit intent for an Activity in your app
		Intent intent = new Intent(this, activity);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Notification.CATEGORY_MESSAGE)
				.setSmallIcon(R.drawable.ic_launcher_foreground)
				.setContentTitle(title)
				.setContentText(message)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true);

		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

		// notificationId is a unique int for each notification that you must define
		notificationManager.notify(notification_id++, builder.build());
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Controller.getInstance();
		createNotificationChannel("Message", "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
	}
}
