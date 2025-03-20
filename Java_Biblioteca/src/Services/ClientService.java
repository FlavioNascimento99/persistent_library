package Services;

import java.util.List;
import jakarta.persistence.EntityManager;

import DAO.ClientDAOImpl;
import Entities.Client;
import Entities.Sale;
import Utils.Database;
import Utils.Input;
import org.apache.log4j.Logger;

public class ClientService {
	private static final Logger logger = Logger.getLogger(ClientService.class);
	private final Input input;
	private final ClientDAOImpl clientDAO;
	private EntityManager manager;

	public ClientService(EntityManager manager, Input input, ClientDAOImpl clientDAO) {
		this.manager = manager;
		this.clientDAO = clientDAO;
		this.input = input;
	}

	public void list() {
		try {
			Database.beginTransaction(manager);
			System.out.println("\n--- Lista de Clientes ---");
			for (Client client : clientDAO.list()) {
				System.out.println(client);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			Database.rollbackTransaction(manager);
		}
	}

	public void create() {
		try {

			Database.beginTransaction(manager);

			// Business Rules 💼
			System.out.println("\n--- Cadastro de Cliente ---");

			String name = input.stringInput("Name: ");
			String cpf = input.stringInput("CPF: ");

			if(clientDAO.isFieldExists("cpf", cpf)) {
				System.out.println("Este ISBN já foi cadastrado.");
				return;
			}

			Client client = new Client(name, cpf);

			clientDAO.save(client);
			Database.commitTransaction(manager);

		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			Database.rollbackTransaction(manager);
		}
	}

//	public void delete() {
//		Database.beginTransaction(manager);
//
//		System.out.println("\n--- Realizar Exclusão - Clientes ---" + manager);
//		List<Client>clientList = clientDAO.list();
//
//		try {
//			if (clientList.isEmpty()) {
//				System.out.println("Não há clientes cadastrados.");
//			} else {
//				for (int i = 0; i < clientList.size(); i++) {
//					System.out.println((i + 1) + ". " + clientList.get(i).getName() + " CPF: " + clientList.get(i).getCpf());
//				}
//
//				int option = input.integerInput("Qual opcao deseja deletar: ");
//				if (option < 1 || option > clientList.size()) {
//					System.out.println("Opção fora de intervalo, tente novamente.");
//					return;
//				}
//
//				Client selectedClient = clientList.get(option - 1);
//				String confirmedClient = input.stringInput("Excluir cliente " + selectedClient.getName() + ", CPF: " + selectedClient.getCpf() + "(s/n): ");
//				if (confirmedClient.equalsIgnoreCase("s")) {
//					clientDAO.delete(selectedClient);
//					Database.commitTransaction(manager);
//					System.out.println("Cliente excluído com sucesso.");
//				} else {
//					System.out.println("Operacao cancelada");
//				}
//
//			}
//		} catch (Exception e) {
//
//			System.out.println("Erro: " + e.getMessage());
//			Database.rollbackTransaction(manager);
//
//		}
//	}

	public void update() {
		Database.beginTransaction(manager);
		System.out.println("\n --- Editar Cliente ---");
		List<Client> clientList = clientDAO.list();

		try {

			if (clientList.isEmpty()) {
				System.out.println("Nao existe cliente cadastrado.");
			} else {

				for(int c = 0; c < clientList.size(); c++) {
					System.out.println(clientList.get(c).getName() + " ID: " + clientList.get(c).getId());
				}

				int option = input.integerInput("Selecione um Cliente: ");
				if (option < 1 || option > clientList.size()) {
					System.out.println("Opcao fora do intervalo, tente novamente.");
					return;
				}
				Client selectedClient = clientList.get(option - 1);

				String setNewClientName = input.stringInput("[ALTERAR - pressione N e o valor será mantido] Nome(Atual): " + selectedClient.getName());
				if (setNewClientName.equalsIgnoreCase("n")) {
					System.out.println("Operacao cancelada, nome sera mantido.");
				} else {
					selectedClient.setName(setNewClientName);
					System.out.println("Cliente alterado com sucesso.");
					clientDAO.update(selectedClient);
				}

				String setNewClientCpf = input.stringInput("[ALTERAR - pressione N e o valor será mantido] CPF(Atual): " + selectedClient.getCpf());
				if (setNewClientCpf.equalsIgnoreCase("n")) {
					System.out.println("Operacao cancelada, nome sera mantido.");
				} else {
					selectedClient.setCpf(setNewClientCpf);
					System.out.println("Cliente alterado com sucesso.");
					clientDAO.update(selectedClient);
				}

				Database.commitTransaction(manager);

			}
		} catch (Exception e) {

			System.out.println("Erro: " + e.getMessage());
			Database.rollbackTransaction(manager);

		}
	}

	public void getClientByMinimumSpent(double minimalSpent) {
		try {

			Database.beginTransaction(manager);
			System.out.println("\n--- Listagem de Clientes que gastaram mais que 500.00 reais em compras ---");

			for (Client client : clientDAO.findClientByMinimumSpent(minimalSpent)) {
				System.out.println(client);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Database.rollbackTransaction(manager);
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
}
