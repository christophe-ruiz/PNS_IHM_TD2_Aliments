package com.example.projetihm.controllers;

import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetihm.R;
import com.example.projetihm.models.Order;
import com.example.projetihm.models.Producer;
import com.example.projetihm.models.users.Consumer;
import com.example.projetihm.models.users.Seller;
import com.example.projetihm.models.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author Gabriel
 */
public class Controller extends Observable {
	private static Controller instance;

	private List<Producer> producers;
	private List<User> users;
	private List<Order> orders;

	private boolean sellerConnected = false;
	private User userConnected;

	public static Controller getInstance() {
		if (instance == null) {
			synchronized (Controller.class) {
				instance = new Controller();
			}
		}
		return instance;
	}

	private Controller() {
		super();
		producers = new ArrayList<>();
		users = new ArrayList<>();
		orders = new ArrayList<>();

		userConnected = null;
	}

	public void setUserConnected(AppCompatActivity activity) {
		userConnected = new Consumer("none", "none", "Joe", "Bobby",
				"", BitmapFactory.decodeResource(activity.getResources(), R.mipmap.avatar_person));

		userConnected.setPhoto(BitmapFactory.decodeResource(activity.getResources(),
				R.mipmap.avatar_person));
	}

	public void setUserConnected (User user) {
		this.userConnected = user;
		setChanged();
		notifyObservers();
	}

	public User getUserConnected() {
		return userConnected;
	}

	public boolean isUserConnected() {
		return userConnected != null;
	}

	public boolean isSellerConnected() {
		return isUserConnected() && userConnected instanceof Seller;
	}
}
