package com.example.projetihm.controllers;

import com.example.projetihm.models.Order;
import com.example.projetihm.models.Producer;
import com.example.projetihm.models.User;

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
	}

	public boolean isSellerConnected() {
		return sellerConnected;
	}

	public void setIsSellerConnected (boolean state) {
		this.sellerConnected = state;
		setChanged();
		notifyObservers();
	}
}
