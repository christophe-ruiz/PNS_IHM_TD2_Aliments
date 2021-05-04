package com.example.projetihm.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * @author Gabriel
 */
public class Basket extends Observable {
	private static Basket instance;

	private double total = 0.0;

	private final HashMap<Product, BasketValue> products = new HashMap<>();

	public static Basket getInstance() {
		if (instance == null) {
			synchronized (Basket.class) {
				instance = new Basket();
			}
		}
		return instance;
	}

	public Basket() {

	}

	public void add(Product p) {
		BasketValue nb = products.get(p);
		if (nb == null) products.put(p, new BasketValue());
		else nb.increment();
		calculateTotal();

		setChanged();
		notifyObservers();
	}

	public void remove(Product p) {
		BasketValue nb = products.get(p);
		if (nb != null) nb.decrement();
		calculateTotal();

		setChanged();
		notifyObservers();
	}

	public void removeAll(Product p) {
		products.remove(p);
		calculateTotal();

		setChanged();
		notifyObservers();
	}

	public boolean isEmpty() {
		return products.size() == 0;
	}

	public void calculateTotal() {
		double total = 0.0;
		for (Map.Entry<Product, BasketValue> p : products.entrySet()) {
			total += p.getValue().get() * p.getKey().getPrix();
		}
		this.total=total;
	}

	public HashMap<Product, BasketValue> getProducts() {
		return products;
	}

	public double getTotal() {
		return total;
	}
}
