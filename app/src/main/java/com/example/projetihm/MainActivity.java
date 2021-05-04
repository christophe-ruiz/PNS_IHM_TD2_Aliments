package com.example.projetihm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetihm.controllers.Controller;
import com.example.projetihm.controllers.SaveMaker;
import com.example.projetihm.factories.UserFactory;
import com.example.projetihm.fragments.MapFragment;

import com.example.projetihm.models.JsonConvertible;
import com.example.projetihm.models.Manager;
import com.example.projetihm.producer.tab.products.ProductsActivity;

import com.example.projetihm.models.users.User;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
	private final MapFragment mapFragment = MapFragment.build();
	private NavigationView navigationDrawerView;
	private Controller controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String json="";
		InputStream is = null;
		try {
			is = getApplicationContext().getAssets().open("products.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			int length = is.read(buffer);
			is.close();
			json = new String(buffer, 0, length, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Manager.loadProducts(json);
		assert getSupportActionBar() != null;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getSupportActionBar().setIcon(R.drawable.outline_menu_24);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("");

		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_place, mapFragment).commit();

		navigationDrawerView =  findViewById(R.id.navigationView);
		navigationDrawerView.setNavigationItemSelectedListener(menuItem -> {
			nav(menuItem);
			((DrawerLayout) findViewById(R.id.drawerLayout)).close();
			return true;
		});

		findViewById(R.id.btn_as_list).setOnClickListener(v ->
				Toast.makeText(MainActivity.this, "Display as list", Toast.LENGTH_SHORT).show());

		controller = Controller.getInstance();
		controller.addObserver(this);

		if (!controller.isUserConnected()) {
			String savedInstance = SaveMaker.readFile(LoginActivity.SAVE_CO_USER_FILE_NAME,
					this);

			if (savedInstance != null) {
				User connectedUser = loadUser(savedInstance);
				if (connectedUser == null) {
					Intent intent = new Intent(this, LoginActivity.class);
					startActivity(intent);
				}
				else {
					Log.d("Projet IHM", "user: " + connectedUser);
					controller.setUserConnected(connectedUser);
				}
			}
			else {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			}
		}

		updateNavigationDrawerView(controller.isSellerConnected());
	}

	private void nav (MenuItem item) {
		if (item.getItemId() == R.id.item_log_out) {
			controller.setUserConnected((User) null);
			SaveMaker.removeFile(LoginActivity.SAVE_CO_USER_FILE_NAME, this);
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		else if (item.getItemId() == R.id.item_details) {
			Intent intent = new Intent(MainActivity.this, UserActivity.class);
			startActivity(intent);
		}
		else if (item.getItemId() == R.id.item_my_products) {
			Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
			startActivity(intent);
		}
		else if (item.getItemId() == R.id.item_orders) {
			Intent intent = new Intent(this, MyOrdersActivity.class);
			startActivity(intent);
		}
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
		else if (item.getItemId() == R.id.search) {
			Intent intent = new Intent(MainActivity.this, SearchActivity.class);
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

	private void updateNavigationDrawerView(boolean sellerConnected) {
		View header = navigationDrawerView.getHeaderView(0);
		if (controller.getUserConnected() != null) {
			((ImageView) header.findViewById(R.id.img_user)).setImageBitmap(
					controller.getUserConnected().getPhoto());
			((TextView) header.findViewById(R.id.tv_user_name)).setText(
					controller.getUserConnected().getFullName());
		}

		if (sellerConnected) {
			navigationDrawerView.getMenu().setGroupVisible(R.id.group_consumer, true);
			navigationDrawerView.getMenu().setGroupVisible(R.id.group_seller, true);
		}
		else {
			navigationDrawerView.getMenu().setGroupVisible(R.id.group_seller, false);
			navigationDrawerView.getMenu().setGroupVisible(R.id.group_consumer, true);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateNavigationDrawerView(controller.isSellerConnected());
	}

	@Override
	public void update(Observable o, Object arg) {
		updateNavigationDrawerView(((Controller) o).isSellerConnected());
	}

	private User loadUser (String jsonFileContent) {
		try {
			JSONObject object = new JSONObject(jsonFileContent);
			String type = object.getString(UserFactory.TYPE);
			return UserFactory.getFactoryFor(type).build(object);
		} catch (JSONException e) {
			Log.d("Projet IHM", e.getMessage());
			return null;
		}
	}
}