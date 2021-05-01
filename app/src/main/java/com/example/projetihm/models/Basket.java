package com.example.projetihm.models;

/**
 * @author Gabriel
 */
public class Basket {
	private static Basket instance;

	public static Basket getInstance() {
		if (instance == null) {
			synchronized (Basket.class) {
				instance = new Basket();
			}
		}
		return instance;
	}

	private Basket() {
		// todo later when we'll have something to add in basket
	}

	public boolean isEmpty() {
		return true;
	}
}
