package dbConnection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import model.Item;

import org.hibernate.Session;


/**
 * @author Lorant
 * Singleton osztály egyetlen példányt készítünk belőle osztálybetöltődéskor.
 */
public class ItemDAO implements AutoCloseable{
	
	
	Session session;
	static ItemDAO instance;
	
	/**
	 * Külső osztályből így férhetünk hozzá a példányhoz.
	 * @return visszaadja az osztály egy példányát
	 */
	public static ItemDAO getInstance() {
		return instance;
	}

	/**
	 * Osztáybetöltődéskor létrehozzzuk az eggyetlen példányunkat
	 */
	static {
		if (instance==null)
		instance=new ItemDAO();
	}
	/**
	 * Eggyetlen példánnyal dolgozunk(ezt biztosítja a private konstruktor) amit a konstruktorban inicializálunk.
	 */
	private ItemDAO(){
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public List<Item> getAllItems(){
		org.hibernate.Query query = session.createQuery("from Item");
		@SuppressWarnings("unchecked")
		ArrayList<Item> items = (ArrayList<Item>) query.list();
		return items;
	}
	
	public void addItem(Item item){
		session.beginTransaction();
		session.save(item);
		session.getTransaction().commit();
	}
	
	public void updateItem(Item item){
		session.beginTransaction();
		session.update(item);
		session.getTransaction().commit();
	}
	
	public void deleteItem(Item item){
		session.beginTransaction();
		session.delete(item);
		session.getTransaction().commit();
	}
	

	public void close() throws Exception {
		session.close();
		
	}
	

}
