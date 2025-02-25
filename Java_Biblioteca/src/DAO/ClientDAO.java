package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Client;

public class ClientDAO {
    private EntityManager manager;

    // Constructors
    public ClientDAO(EntityManager manager) {this.manager = manager;}



    public void save(Client client, EntityManager manager) {

        manager.persist(client);

    }

    public void update(Client client, EntityManager manager) {
        if(client == null) {
            throw new IllegalArgumentException("DAO: client cannot be null");
        }

        manager.merge(client);
    }

    public void delete(Client client, EntityManager manager) {

        TypedQuery<Client> clientQuery = manager.createQuery("SELECT c FROM Client c WHERE c.cpf = :Client", Client.class);
        clientQuery.setParameter("Client", client);
        manager.remove(clientQuery);

    }

    public List<Client> list() {

    	TypedQuery<Client> clientQuery = manager.createQuery("SELECT c FROM Client c", Client.class);
    	List<Client> resultQuery = clientQuery.getResultList();
    	return resultQuery;

    }

    // cpf as id 💀
    public Client search(String cpf) {

    	TypedQuery<Client> clientQuery = manager.createQuery("SELECT c FROM Client c WHERE c.cpf = :cpf", Client.class);
    	clientQuery.setParameter("cpf", cpf);
    	return clientQuery.getSingleResult();

    }

}
