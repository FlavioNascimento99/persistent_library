package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Client;
import Entities.Sale;
import Utils.Database;

public class SaleDAO {
	private EntityManager manager;
	
	public SaleDAO(){}
	
	public void save(Sale sale) {
		manager.persist(sale);
		manager.getTransaction().commit();
		manager.close();
	}

	public List<Sale> listAll() {
		TypedQuery<Sale> saleQuery = manager.createQuery("select s from Sale s", Sale.class);
		List<Sale> resultQuery = saleQuery.getResultList();
		return resultQuery;
	}
	
	public Sale searchById(int id) {
		TypedQuery<Sale> saleQuery = manager.createQuery("select s from Sale s where s.id = :id", Sale.class);
		saleQuery.setParameter("id", id);
		return saleQuery.getSingleResult();
	}

// 	I don't remember how, when or why i've created this.
	
//	public List<Sale> listClient(Client client) {
//		Query query = database.query();
//		query.constrain(Sale.class);
//		query.descend("cliente").constrain(client);
//		return query.execute();
//	}

	
// 	I was completelly out of my miind creating this one, wtf is "delete Sale"? tax evasion? 
	
//	public void delete(Sale sale) {
//		TypedQuery<Sale> saleQuery = manager.createQuery("select s from Sale s where s.id = :id", Sale.class);
//		saleQuery.setParameter("id", Sale.id);
//    }
}