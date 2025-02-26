package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Sale;

public class SaleDAO {

	private EntityManager manager;

	public SaleDAO(EntityManager manager) {

		this.manager = manager;

	}



	public void save(Sale sale, EntityManager manager) {

		manager.merge(sale);

	}
	public List<Sale> list() {
		TypedQuery<Sale> saleQuery = manager.createQuery("select s from Sale s", Sale.class);
		List<Sale> resultQuery = saleQuery.getResultList();
		return resultQuery;
	}


	public Sale search(int id) {
		TypedQuery<Sale> saleQuery = manager.createQuery("SELECT s FROM Sale s WHERE s.id = :id", Sale.class);
		saleQuery.setParameter("id", id);
		return saleQuery.getSingleResult();
	}

//	public List<Sale> listClient(Client client) {
//		Query query = database.query();
//		query.constrain(Sale.class);
//		query.descend("cliente").constrain(client);
//		return query.execute();
//	}

}