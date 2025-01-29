package DAO;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import Entities.Client;

public class ClientDAO {
    private ObjectContainer database;

    public ClientDAO(ObjectContainer database) {
        this.database = database;
    }

    public void save(Client client) {
        database.store(client);
        database.commit();
    }

    public Client searchByCpf(String cpf) {
        Query query = database.query();
        query.constrain(Client.class);        
        query.descend("cpf").constrain(cpf);
        List<Client> resultado = query.execute();
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public List<Client> listAll() {
        Query query = database.query();
        query.constrain(Client.class);
        return query.execute();
    }

    public void delete(Client toDeleteClient) {
        Query query = database.query();
        query.constrain(Client.class);
        query.descend("cpf").constrain(toDeleteClient);
        database.delete(toDeleteClient);
        database.commit();
    }
}
