package Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/*
 * Ler sobre
 * 
 * - O que e factory e manager dentro deste contexto?
 * 		Factory cria instancias de Manager. 
 * 
*/

public class Database {
    private static EntityManagerFactory factory;

    static {
    	try {
    		factory = Persistence.createEntityManagerFactory("hibernate-mysql");
    	} catch(Exception e) {
    		throw new RuntimeException("Erro ao inicializar o EntityManagerFactory" + e.getMessage());
    	}
    }
    
    public static EntityManager openConnection() {
    	return factory.createEntityManager();
    }
    
    public static void closeConnection(EntityManager manager) {
    	if(manager != null && manager.isOpen()) {
    		manager.close();
    	}
    }
    
    public static void shutdown() {
    	if(factory != null && factory.isOpen()) {
    		factory.close();
    	}
    }
}



