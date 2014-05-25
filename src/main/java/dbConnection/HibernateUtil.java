package dbConnection;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * 
 * @author Lorant
 *A SessionFactoryhoz való hozzáférést biztosító osztály.
 */
public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	/**
	 * Osztálybetöltődéskor lefut elvégzi a konfigurációkat és felállítja a SessionFactory-t
	 */
	static{
		Configuration configuration = new Configuration().configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	public static SessionFactory getSessionFactory(){
		
		return sessionFactory;
	}
	
	public static void shutdown(){
		sessionFactory.close();
	}
	
}
