package Services;

import java.util.List;

import DAO.ClientDAO;
import Entities.Client;

import Utils.DatabaseUtils;
import Utils.InputUtils;

public class ClientService {
	private InputUtils inputUtils;
	private ClientDAO clientDAO;
	
	public ClientService(ClientDAO clientDAO, InputUtils inputUtils) {
		this.clientDAO = clientDAO;
		this.inputUtils = inputUtils;
	}
	
	public void listAll() {
		System.out.println("\n--- Lista de Clientes ---");
		ClientDAO clientDAO = new ClientDAO(DatabaseUtils.openDatabase());
		for (Client client : clientDAO.listAll()) {
			System.out.println(client);
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
		System.out.println("\n--- Realizar Exclusão - Clientes ---");;
        clientDAO = new ClientDAO(DatabaseUtils.openDatabase());
        List<Client> clientList = clientDAO.listAll();
        if (clientList.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
            return;
        }
        for( int i=0;i < clientList.size(); i++) {
        	System.out.println((i + 1) +". " + clientList.get(i).getName() + " CPF: " + clientList.get(i).getCpf());
        }
        int option;
        try {
        	option = inputUtils.integerInput("Qual opcao deseja deletar: ");
        } catch (NumberFormatException  e) {
        	System.out.println("Opção inválida. Tente novamente.");
        	return;
        }
        
        if (option < 1 || option > clientList.size()) {
        	System.out.println("Opção fora de intervalo, tente novamente.");
        	return;
        }
        
        Client selectedClient = clientList.get(option -1);
        System.out.print("Tem certeza de qe deseja excluir o cliente " + selectedClient.getName() + ", CPF: " + selectedClient.getCpf() + "(s/n): ");
        
        String confirmacao = inputUtils.stringInput("");
        if (confirmacao.equalsIgnoreCase("s")) {
        	clientDAO.delete(selectedClient);
        	System.out.println("Cliente excluído com sucesso.");
        } else {
        	System.out.println("Operação cancelada.");
    	}
    }
}
