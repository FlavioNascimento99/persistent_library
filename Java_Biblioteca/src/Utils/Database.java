package Utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Database {
	private static EntityManager manager;
	private static EntityManagerFactory factory;

    static {

    	try {
    		factory = Persistence.createEntityManagerFactory("hibernate-mysql");
    	} catch ( Exception e ) {
    		throw new RuntimeException("Erro ao inicializar o EntityManagerFactory" + e.getMessage());
    	}

	}

	public static EntityManager getEntityManager() {
		 if (manager == null|| !manager.isOpen()) {
			 manager = factory.createEntityManager();
		 }
		 return manager;
	}


	//==============================================//
	//					Transactions				//
	//==============================================//
	public static void beginTransaction(EntityManager manager) {
		if (manager != null && !manager.getTransaction().isActive()) {
			manager.getTransaction().begin();
		}
	}

	public static void commitTransaction(EntityManager manager) {
		if (manager != null && manager.getTransaction().isActive()) {
			manager.getTransaction().commit();
		}
	}

	public static void rollbackTransaction(EntityManager manager) {
		if (manager != null && manager.getTransaction().isActive()) {
			manager.getTransaction().rollback();
		}
	}





	//==============================================//
	//				Connection Manager				//
	//==============================================//
    public static EntityManager openConnection() {

		return factory.createEntityManager();

	}
    
    public static void closeConnection(EntityManager manager) {
		if ( manager != null && manager.isOpen() ) {
    		manager.close();
    	}
	}




	//==============================================//
	//				Factory Shutdown				//
	//==============================================//
    public static void shutdown() {
    	if(factory != null && factory.isOpen()) {
    		factory.close();
    	}
    }

}



