package com.example.projetihm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projetihm.fragments.LocationFragment;
import com.example.projetihm.fragments.MapFragment;
import com.example.projetihm.producer.ProducerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
	private boolean isOnMap = false;
	private final MapFragment mapFragment = MapFragment.build();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(this::linkNavigation);

		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_place, mapFragment).commit();

		findViewById(R.id.basket_fab).setOnClickListener(v ->
				makeNotification());
	}

	@SuppressLint("NonConstantResourceId")
	private boolean linkNavigation(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.user_tab:
				Toast.makeText(getApplicationContext(), "User tab", Toast.LENGTH_SHORT).show();
				Intent intentSend = new Intent(getApplicationContext(), ProducerActivity.class);
				startActivity(intentSend);
				break;
			case R.id.list_tab:
				if (isOnMap) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.fragment_place, LocationFragment.build()).commit();
					item.setIcon(R.drawable.baseline_map_24);
				}
				else {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.fragment_place, mapFragment).commit();
					item.setIcon(R.drawable.baseline_list_24);
				}
				isOnMap = !isOnMap;
				break;
			case R.id.favourite_tab:
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_place, mapFragment).commit();
				Toast.makeText(getApplicationContext(), "Favourite tab", Toast.LENGTH_SHORT).show();
				break;
			case R.id.search_tab:
				Toast.makeText(getApplicationContext(), "Search tab", Toast.LENGTH_SHORT).show();
				break;
			default:
				return false;
		}
		return true;
	}

	private static int notification_id = 0;
	private void makeNotification() {
		// Create an explicit intent for an Activity in your app
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Notification.CATEGORY_MESSAGE)
				.setSmallIcon(R.drawable.ic_launcher_foreground)
				.setContentTitle("Ma notification")
				.setContentText("Un message")
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true);

		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

		// notificationId is a unique int for each notification that you must define
		notificationManager.notify(notification_id++, builder.build());
	}
}