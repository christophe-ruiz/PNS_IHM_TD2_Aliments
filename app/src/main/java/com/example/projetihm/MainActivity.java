package com.example.projetihm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projetihm.fragments.LocationFragment;
import com.example.projetihm.fragments.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
	private final MapFragment mapFragment = MapFragment.build();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getSupportActionBar().setIcon(R.drawable.outline_menu_24);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setSelectedItemId(R.id.map_tab); // Change selected tab
		navigation.setOnNavigationItemSelectedListener(this::linkNavigation);

		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_place, mapFragment).commit();

		/*findViewById(R.id.topAppBar).setOnClickListener(v ->
				((DrawerLayout) findViewById(R.id.drawerLayout)).open()
		);*/

		((NavigationView) findViewById(R.id.navigationView)).setNavigationItemSelectedListener(menuItem -> {
			menuItem.setChecked(true);
			((DrawerLayout) findViewById(R.id.drawerLayout)).close();
			return true;
		});
	}

	@SuppressLint("NonConstantResourceId")
	private boolean linkNavigation(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.user_tab:
				Toast.makeText(getApplicationContext(), "User tab", Toast.LENGTH_SHORT).show();
				break;
			case R.id.list_tab:
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_place, LocationFragment.build()).commit();
				break;
			case R.id.map_tab:
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_place, mapFragment).commit();
				break;
			case R.id.favourite_tab:
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.fragment_place, mapFragment).commit();
				Toast.makeText(getApplicationContext(), "Favourite tab", Toast.LENGTH_SHORT).show();
				break;
			case R.id.search_tab:
				//Toast.makeText(getApplicationContext(), "Search tab", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				startActivity(intent);
				break;
			default:
				return false;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.basket_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.basket) {
			Intent intent = new Intent(MainActivity.this, BasketActivity.class);
			startActivity(intent);

			return true;
		}
		else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onSupportNavigateUp() {
		((DrawerLayout) findViewById(R.id.drawerLayout)).open();
		return super.onSupportNavigateUp();
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