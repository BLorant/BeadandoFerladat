package dbConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.swing.JOptionPane;

import main.Main;
import model.Item;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Lorant
 * 
 * Ezen az osztályon keresztül férhetünk hozzá az adatbázisban tárolt adatokhoz.
 * Singleton osztály egyetlen példányt készítünk belőle osztálybetöltődéskor.
 */
public class ItemDAO implements AutoCloseable{
	
	
	Session session;
	static ItemDAO instance;
	private static Logger logger = LoggerFactory.getLogger(ItemDAO.class);
	
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
		try{
		session = HibernateUtil.getSessionFactory().openSession();
		}catch(ExceptionInInitializerError e){
			JOptionPane.showMessageDialog(null, "Sorry can't connect to database",
					"Error", JOptionPane.ERROR_MESSAGE);
			logger.error("Cant connect to database");
			System.exit(1);
		}
	}
	
	/**
	 * Az adatbázisból lekér minden tárolt itemet.
	 * @return visszaad egy itemekbő álló listát.
	 */
	
	public void savePoint(String s){

	}
	
	public List<Item> getAllItems(){
		try{
		org.hibernate.Query query = session.createQuery("from Item");
		@SuppressWarnings("unchecked")
		ArrayList<Item> items = (ArrayList<Item>) query.list();
		return items;
		}
		catch(HibernateException e){
			Main.getFrame().showError("A hozzáadás sikertelen volt: "+e.getMessage());
		}
		return null;
	}
	
	/**
	 * Felvesz egy új itemet az adatbázisba.
	 * @param item a hozzáadni kívánt item.
	 */
	public void addItem(Item item){
		try{
		session.beginTransaction();
		session.save(item);
		session.getTransaction().commit();
		}
		catch(HibernateException e){
			Main.getFrame().showError("A hozzáadás sikertelen volt: "+e.getMessage());
		}
		}
	
	/**
	 * Frissít egy itemet.
	 * @param item A frissíteni kívánt item.
	 */
	public void updateItem(Item item){
		try{
	
		session.beginTransaction();
		session.update(item);
		session.getTransaction().commit();
	}
	catch(HibernateException e){
		Main.getFrame().showError("A hozzáadás sikertelen volt: "+e.getMessage());
	}}
	
	/**
	 * Töröl egy itemet.
	 * @param item a törölni kívánt item.
	 */
	public void deleteItem(Item item){
		try{
	
		session.beginTransaction();
		session.delete(item);
		session.getTransaction().commit();
		}
		catch(HibernateException e){
			Main.getFrame().showError("A hozzáadás sikertelen volt: "+e.getMessage());
		}
	}
	
	
	/**
	 * A Session mindenképpenvaló lezárására szolgál.
	 */
	public void close() {
		session.close();
		
	}
	

}
