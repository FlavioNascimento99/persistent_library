package Interfaces;

import Entities.Client;

import java.util.List;

public interface ClientDAO {
    void save(Client client);
    void update(Client client);
    void delete(Client client);

    Client search(Integer clientId);
    List<Client> findClientByMinimumSpent(Double minimumSpent);
    Double getTotalSpentByClient(Integer clientId);
}
