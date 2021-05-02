package com.example.projetihm.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriel
 */
public class Basket {
	private static Basket instance;

	private static int total = 0;

	private HashMap<Product, BasketValue> products = new HashMap<>();

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
	}

	public void remove(Product p) {
		BasketValue nb = products.get(p);
		if (nb != null) nb.decrement();
		calculateTotal();
	}

	public void removeAll(Product p) {
		products.remove(p);
		calculateTotal();
	}

	public boolean isEmpty() {
		return products.size() == 0;
	}

	public void calculateTotal() {
		total = 0;
		for (Map.Entry<Product, BasketValue> p : products.entrySet()) {
			total += p.getValue().get() * p.getKey().getPrix();
		}
	}

	public static int getTotal() {
		return total;
	}
}
