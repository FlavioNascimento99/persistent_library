package Services;

import java.util.List;

import javax.persistence.EntityManager;

import DAO.ClientDAO;
import Entities.Client;

import Utils.Database;
import Utils.Input;

public class ClientService {
	private Input inputUtils;
	private ClientDAO clientDAO;
	private EntityManager manager;
	
	public ClientService() {}
	public ClientService(EntityManager manager) {}
	public ClientService(ClientDAO clientDAO, Input input) {}
	
	public void listAll() {
		try {
			manager = Database.openConnection();
			System.out.println("\n--- Lista de Clientes ---");
			for (Client client : clientDAO.listAll()) {
				System.out.println(client);
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e.getMessage());
		}
	}

	public void addClient() {
		System.out.println("\n--- Cadastro de Cliente ---");
		
		String name = inputUtils.stringInput("Name: ");
		String cpf = inputUtils.stringInput("CPF: ");
		
		Client client = new Client(name, cpf);
		
		clientDAO.save(client);
		
        System.out.println("Cliente cadastrado com sucesso!\n");
    }

	
	public void deleteClient() {
		manager = Database.openConnection();
		System.out.println("\n--- Realizar Exclusão - Clientes ---");
        List<Client> clientList = clientDAO.listAll();
        
        if (clientList.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
            return;
        } else {
        	for( int i=0;i < clientList.size(); i++) {
            	System.out.println((i + 1) +". " + clientList.get(i).getName() + " CPF: " + clientList.get(i).getCpf());
            }
        	
            int option;
            
            try {
            	option = inputUtils.integerInput("Qual opcao deseja deletar: ");
            	if (option < 1 || option > clientList.size()) {
                	System.out.println("Opção fora de intervalo, tente novamente.");
                	return;
                }
            	
            	Client selectedClient = clientList.get(option -1);
                System.out.println("Tem certeza de qe deseja excluir o cliente " 
                		+ selectedClient.getName() + ", CPF: " + selectedClient.getCpf() + "(s/n): ");
                
                String confirmacao = inputUtils.stringInput("");
                
                if (confirmacao.equalsIgnoreCase("s")) {
                	clientDAO.delete(selectedClient);
                	System.out.println("Cliente excluído com sucesso.");
                } else {
                	System.out.println("Operação cancelada.");
            	}
                
            } catch (NumberFormatException  e) {
            	System.out.println("Opção inválida. Tente novamente.");
            	return;
            }            
        }
        
        
        
    }
}
