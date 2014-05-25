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
	/**
	 * Az adatbázisban az elsődleges kulcs és programon belül is ezt használjuk pontos azonosításra.
	 */
	@Id
	@GeneratedValue
	private int id;
	/**
	 * Az item neve.
	 */
	private String name;
	/**
	 * Az adoot itemből a mennyiség tárolása.
	 */
	private int quantity;
	/**
	 * Az adott item ára darabonként.
	 */
	private int price;
	/**
	 * egy általános leírás az itemről.
	 */
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
	 * Az Equals felülírva ,hogy az id alapján hasonlítsa össze a példányokat.
	 * @param obj az összehasonlítani kívánt objektum.
	 * @return true ha megeggyezik a két objektum false ha nem.
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

	/**
	 * Az adott item mennyiségének megváltoztatása.
	 * @param n a csökkentés vagy növelés mennyisége. negatív szám esetén csökkentünk.
	 */
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

	/**
	 * Egy toString amit a felugró ablakok használnak az itemek kiiratására.
	 * @return az adattagok értéke sztriggé fűzve.
	 */
	public String toString2() {
		return "id=" + id + ", name=" + name + ", quantity=" + quantity
				+ ", price=" + price ;
	}
	
	/**
	 * Csak az item neve amit a listában való kiiratáshoz használok.
	 * @return az adattagok értéke sztriggé fűzve.
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Konstruktor az adattagok megadásával.
	 * @param name név
	 * @param quantity mennyiség
	 * @param price  ár
	 * @param description leírás
	 */
	public Item(String name, int quantity, int price, String description) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.description = description;
	}

	/**
	 * Alap konstruktor "üress" item létrehozásához.
	 */
	public Item() {
	}

	public Item(int id, String name, int quantity, int price, String description) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.description = description;
	}

	
	
}
