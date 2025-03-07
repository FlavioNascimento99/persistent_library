package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Client;
import org.apache.log4j.Logger;
import org.hibernate.annotations.ParamDef;

public class ClientDAO {
    private static final Logger logger = Logger.getLogger(ClientDAO.class);

    private final EntityManager manager;

    public ClientDAO(EntityManager manager) {this.manager = manager;}

    public void save(Client client, EntityManager manager) {

        manager.merge(client);

    }

    public void update(Client client, EntityManager manager) {
        if(client == null) {
            throw new IllegalArgumentException("DAO: client cannot be null");
        }

        manager.merge(client);
    }

    public void delete(Client client, EntityManager manager) {
        client = manager.find(Client.class, client.getId());
        if(client != null) {
            manager.remove(client);
        } else {
            System.out.println("DAO: client not found");
        }

    }

    public List<Client> list() {

    	TypedQuery<Client> clientQuery = manager.createQuery("SELECT c FROM Client c", Client.class);
    	List<Client> resultQuery = clientQuery.getResultList();
    	return resultQuery;

    }

    public Client search(Integer clientId) {

    	TypedQuery<Client> clientQuery = manager.createQuery("SELECT c FROM Client c WHERE c.id = :id", Client.class);
    	clientQuery.setParameter("id", 1L);
    	return clientQuery.getSingleResult();

    }

    // Nome terrivel. @TODO: pensar em um nome melhor
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
