package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
/**
 * Ez az osztály szolgál alapul az adatok tárolásához és kezeléséhez.
 * @author Lorant
 *
 */
@ Entity
public class Item {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int quantity;
	private int price;
	private String description;
	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	/**
	 * equals flülírva ,hogy csak az id alapján hasonlítsa össze a példányokat.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void changeQuantity(int n){
		quantity=quantity+n;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String toString2() {
		return "id=" + id + ", name=" + name + ", quantity=" + quantity
				+ ", price=" + price ;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public Item(String name, int quantity, int price, String description) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.description = description;
	}

	public Item() {
	}

	
	
}
