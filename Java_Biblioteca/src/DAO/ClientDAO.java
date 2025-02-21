package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Client;

public class ClientDAO {
    private EntityManager manager;

    public ClientDAO() {}

    public void save(Client client) {
        manager.persist(client);
        manager.getTransaction().commit();
        manager.close();
    }

    public List<Client> listAll() {
    	TypedQuery<Client> clientQuery = manager.createQuery("select c from Client c", Client.class);
    	List<Client> resultQuery = clientQuery.getResultList();
    	return resultQuery;
    }
    
    public Client searchByCpf(String cpf) {
    	TypedQuery<Client> clientQuery = manager.createQuery("select c from Client c where c.cpf = :cpf", Client.class);
    	clientQuery.setParameter("cpf", cpf);
    	return clientQuery.getSingleResult();
    }
    
    
    // Maybe this one doesn't make sense, we just know if that's insane or not after we've done this manager transition.
    public void delete(Client client) {
    	TypedQuery<Client> clientQuery = manager.createQuery("select c from Client c wherer c.cpf = :Client", Client.class);
    	clientQuery.setParameter("Client", client);
    	manager.remove(clientQuery);
    }
}
