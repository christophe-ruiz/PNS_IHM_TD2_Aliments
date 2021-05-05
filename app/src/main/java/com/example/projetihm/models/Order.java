package com.example.projetihm.models;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author Gabriel
 */
public class Order {

	@Override
	public String toString() {
		String returned= "Commande n°" + id + "\n";
		for(Map.Entry<Product,BasketValue> entry : products.entrySet()){
			returned+="\t"+entry.getValue().toString()+"× "+entry.getKey().toString()+"\n";
		}
		returned+="Total: "+getTotalPrice()+"€";
		return returned;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return id == order.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public enum Status {
		RUNNING, RECEIVED,READY, DELIVERED
	}

	private final long id;
	private final Map<Product, BasketValue> products;
	private Status status = Status.RUNNING;

	public Order (long id, Map<Product, BasketValue> products) {
		this.id = id;
		this.products = products;
	}

	public long getId() {
		return id;
	}

	public List<Product> getProducts() {
		return new ArrayList<>(products.keySet());
	}

	public double getTotalPrice () {
		double tot = 0;
		BasketValue count;
		for (Product product : products.keySet()) {
			count = products.get(product);
			if (count != null) {
				tot += (count.get() * product.getPrix());
			}
		}

		return tot;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getProductCount(Product product) {
		BasketValue count = products.get(product);
		if (count != null) {
			return count.get();
		}
		return 0;
	}

	public int getTotalProductCount() {
		int tot = 0;

		for (Product p : products.keySet()) {
			tot += getProductCount(p);
		}

		return tot;
	}

}
