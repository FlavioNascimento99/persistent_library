package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Client;
import org.apache.log4j.Logger;
import org.hibernate.annotations.ParamDef;

public class ClientDAO extends GenericDAO<Client, Integer> {
    public ClientDAO(EntityManager manager) {
        super(manager, Client.class);
    }


    public Client search(Integer clientId) {
    	TypedQuery<Client> clientQuery = manager.createQuery("SELECT c FROM Client c WHERE c.id = :id", Client.class);
    	clientQuery.setParameter("id", 1L);
    	return clientQuery.getSingleResult();
    }

    public List<Client> findClientByMinimumSpent(Double minimalSpent) {
        String jpql = "SELECT c FROM Client c JOIN c.salesList s GROUP BY c.id, c.name HAVING SUM(s.totalValue) > : minimalSpent";
        TypedQuery<Client> clientQuery = manager.createQuery(jpql, Client.class);
        clientQuery.setParameter("minimalSpent", minimalSpent);
        return clientQuery.getResultList();
    }

    public Double getTotalSpentByClient(Integer clientId) {
        String jpql = "SELECT SUM(s.totalValue) FROM Client c JOIN c.salesList s WHERE c.id = :clientId";
        TypedQuery<Double> clientQuery = manager.createQuery(jpql, Double.class);
        clientQuery.setParameter("clientId", clientId);
        Double queryResult = clientQuery.getSingleResult();
        return queryResult != null ? queryResult : 0.0;
    }

}
