package Services;

import java.util.List;

import javax.persistence.EntityManager;

import DAO.ClientDAO;
import Entities.Client;

import Entities.Sale;
import Utils.Database;
import Utils.Input;
import org.apache.log4j.Logger;

public class ClientService {
	private static final Logger logger = Logger.getLogger(ClientService.class);
	private final Input input;
	private final ClientDAO clientDAO;
	private EntityManager manager;
	
	public ClientService(ClientDAO clientDAO, Input input) {
		this.clientDAO = clientDAO;
		this.input = input;
	}


	public void list() {
		
		try {

			manager = Database.openConnection();

			System.out.println("\n--- Lista de Clientes ---");

			for (Client client : clientDAO.list()) {

				System.out.println(client);

			}

		} finally {
			
			Database.closeConnection(manager);

		}
	}
	public void create() {

		try {
			manager = Database.openConnection();
			manager.getTransaction().begin();

			System.out.println("\n--- Cadastro de Cliente ---");

			String name = input.stringInput("Name: ");


			Client client = new Client(name);

			clientDAO.save(client, manager);
			manager.getTransaction().commit();
			System.out.println("Cliente cadastrado com sucesso!\n");

		}


		catch(Exception e) {

			System.out.println("Erro: " + e.getMessage());

			if (manager.getTransaction().isActive()) {

				manager.getTransaction().rollback();

			}

		}

		finally {

			System.out.println("\nClientes cadastrados com sucesso!\n");
			Database.closeConnection(manager);

		}
	}
	public void delete() {

		try {

			manager = Database.openConnection();
			manager.getTransaction().begin();

			System.out.println("\n--- Realizar Exclusão - Clientes ---");

			List<Client> clientList = clientDAO.list();

			if (clientList.isEmpty()) {

				System.out.println("Não há clientes cadastrados.");

			} else {

				for( int i=0;i < clientList.size(); i++) {

					System.out.println((i + 1) +". " + clientList.get(i).getName() + " ID: " + clientList.get(i).getId());

				}

				int option;

				option = input.integerInput("Qual opcao deseja deletar: ");
				if (option < 1 || option > clientList.size()) {

					System.out.println("Opção fora de intervalo, tente novamente.");

				}

				Client selectedClient = clientList.get(option -1);
				String confirmedClient = input.stringInput("Tem certeza de qe deseja excluir o cliente " + selectedClient.getName() + ", CPF: " + selectedClient.getId() + "(s/n): ");

				if (confirmedClient.equalsIgnoreCase("s")) {
					clientDAO.delete(selectedClient, manager);
					manager.getTransaction().commit();
					System.out.println("Cliente excluído com sucesso.");
				} else {
					System.out.println("Operação cancelada.");
				}
			}
		}
		catch( Exception e) {

			System.out.println("Erro: " + e.getMessage());
			manager.getTransaction().rollback();

		} finally {

			Database.closeConnection(manager);

		}
	}
	public void update() {

		manager = Database.openConnection();
		manager.getTransaction().begin();

		System.out.println("\n --- Editar Cliente ---");
		List<Client> clientList = clientDAO.list();

		try {

			if(clientList.isEmpty()) {

				System.out.println("Nao existe cliente cadastrado.");

			} else {

				for(int c = 0; c < clientList.size(); c++) {

					System.out.println(clientList.get(c).getName() + " ID: " + clientList.get(c).getId());

				}

				int option;

				option = input.integerInput("Selecione um Cliente: ");

				if (option < 1 || option > clientList.size()) {

					System.out.println("Opcao fora do intervalo, tente novamente.");
					return;

				}

				Client selectedClient = clientList.get(option -1);
				String setNewClientName = input.stringInput("Deseja alterar o nome do Cliente em questao? Nome Atual" + selectedClient.getName());
				if (setNewClientName.equalsIgnoreCase("n")) {

					System.out.println("Operacao cancelada, nome sera mantido.");

				} else {
					selectedClient.setName(setNewClientName);
					System.out.println("Cliente alterado com sucesso.");

					clientDAO.update(selectedClient, manager);
				}

			}

		}

		catch (Exception e) {

			System.out.println("Erro: " + e.getMessage());

			manager.getTransaction().rollback();

		}

		finally {

			manager.getTransaction().commit();

			Database.closeConnection(manager);

		}

	}
	public void getClientByMinimumSpent(double minimalSpent) {

		try {
			manager = Database.openConnection();

			System.out.println("\n--- Listagem de Clientes que gastaram mais que 500.00 reais em compras ---");

			for(Client client: clientDAO.findClientByMinimumSpent(minimalSpent)) {

				System.out.println(client);

			}
		} finally {

			Database.closeConnection(manager);

		}
	}
	public Double getTotalSpentByClient(Integer clientId) {
		Client client = clientDAO.search(clientId);
		if (client == null) {
			return 0.0;
		}

		return client.getSalesList().stream()
				.mapToDouble(Sale::getTotalValue)
				.sum();
	}


	public void search() {
		manager = Database.openConnection();
		System.out.println("\n--- Busca Clientes ---");
	}
}
