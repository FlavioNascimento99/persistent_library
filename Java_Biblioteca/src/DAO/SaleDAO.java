package DAO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Sale;
import org.apache.log4j.Logger;

public class SaleDAO {
	private static final Logger logger = Logger.getLogger(SaleDAO.class);
	private EntityManager manager;


	// Construtor
	public SaleDAO(EntityManager manager) {
		this.manager = manager;
	}




	/**
	 * Méotodo de salvamento por meio de JPA.
	 * 
	 */
	public void save(Sale sale, EntityManager manager) {
		manager.merge(sale);
	}




	/**
	 * Método de listagem padrão por meio de código JPQL
	 * 
	 */
	public List<Sale> list() {
		TypedQuery<Sale> saleQuery = manager.createQuery("SELECT s FROM  Sale s", Sale.class);
		List<Sale> resultQuery = saleQuery.getResultList();
		return resultQuery;
	}




	/**
	 * Método de listagem por meio de LocalDate de inicio e fim
	 */
	public List<Sale> listByDate(LocalDate dateStart, LocalDate dateEnd) {
		TypedQuery<Sale> saleQuery = manager.createQuery("SELECT s FROM Sale s WHERE s.dateSale BETWEEN :dateStart AND :dateEnd", Sale.class);
		saleQuery.setParameter("dateStart", dateStart);
		saleQuery.setParameter("dateEnd", dateEnd);
		return saleQuery.getResultList();
	}




	/**
	 * Método de busca de venda por meio de Id da mesma (INUTILIZADO)
	 * 
	 */
	public Sale search(int id) {
		TypedQuery<Sale> saleQuery = manager.createQuery("SELECT s FROM Sale s WHERE s.id = :id", Sale.class);
		saleQuery.setParameter("id", id);
		return saleQuery.getSingleResult();
	}

}