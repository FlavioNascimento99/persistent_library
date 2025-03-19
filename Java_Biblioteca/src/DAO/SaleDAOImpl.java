package DAO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Sale;

public abstract class SaleDAOImpl extends GenericDAOImpl<Sale, Integer> {

	public SaleDAOImpl(EntityManager manager) {
		super(manager, Sale.class);
	}

	public List<Sale> listByDate(LocalDate dateStart, LocalDate dateEnd) {
		TypedQuery<Sale> saleQuery = manager.createQuery("SELECT s FROM Sale s WHERE s.dateSale BETWEEN :dateStart AND :dateEnd", Sale.class);
		saleQuery.setParameter("dateStart", dateStart);
		saleQuery.setParameter("dateEnd", dateEnd);
		return saleQuery.getResultList();
	}

}