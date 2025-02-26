package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Client;

public class ClientDAO {
    private EntityManager manager;

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

}
