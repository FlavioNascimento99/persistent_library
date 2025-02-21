package Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/*
 * Ler sobre
 * 
 * - Persistence.createEntityManagerFactory()
 * - O que e factory e manager dentro deste contexto?
 * - 
 * 
*/

public class Database {
	private static EntityManager manager;
    private static EntityManagerFactory factory;

    public static EntityManager openConnection() {
    	if (factory == null) {
    		factory = Persistence.createEntityManagerFactory("hibernate-mysql");
    		manager = factory.createEntityManager();
    	}		
		return manager;
    }
    
    public static void closeConnection() {
    	if(factory != null && manager.isOpen()) {
    		manager.close();
    		factory.close();
    		
    		manager = null;
    	}
    }
    
}



