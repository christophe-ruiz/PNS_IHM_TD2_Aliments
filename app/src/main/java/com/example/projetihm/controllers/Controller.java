package com.example.projetihm.controllers;

import android.graphics.BitmapFactory;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetihm.R;
import com.example.projetihm.models.BasketValue;
import com.example.projetihm.models.Order;
import com.example.projetihm.models.Producer;
import com.example.projetihm.models.Product;
import com.example.projetihm.models.users.Consumer;
import com.example.projetihm.models.users.Seller;
import com.example.projetihm.models.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * @author Gabriel
 */
public class Controller extends Observable {
	private static Controller instance;

	private List<Producer> producers;
	private List<User> users;
	private final List<Order> orders;

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
		orders = mockOrders(); //new ArrayList<>();
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

	public List<Order> getOrders() {
		return orders;
	}

	public Order getOrderById (long id) {
		for (Order order : orders) {
			if (order.getId() == id) {
				return order;
			}
		}
		return null;
	}

	private static List<Order> mockOrders () {
		List<Order> orders = new ArrayList<>();
		Map<Product, BasketValue> products = new HashMap<>();
		products.put(new Product(R.mipmap.pomme, "Pomme", "Marseille", 2.,
				"De belles pommes rouges", true, false), new BasketValue(2));
		orders.add(new Order(101214, products));
		orders.add(new Order(694201, new HashMap<>()));
		orders.get(orders.size() - 1).setStatus(Order.Status.RECEIVED);
		orders.add(new Order(998654, new HashMap<>()));
		orders.get(orders.size() - 1).setStatus(Order.Status.RECEIVED);

		return orders;
	}
}