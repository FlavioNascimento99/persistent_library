package Services;

import java.util.List;
import java.util.Scanner;

import DAO.ClienteDAO;
import Entities.Cliente;
import Utils.DatabaseUtils;

public class ClienteService {

	private static void listarClientes() {
		System.out.println("\n--- Lista de Clientes ---");
		ClienteDAO clienteDAO = new ClienteDAO(DatabaseUtils.openDatabase());
		for (Cliente cliente : clienteDAO.listarTodos()) {
			System.out.println(cliente);
		}
		System.out.println();
	}

	private static void cadastrarCliente(Scanner scanner) {
        System.out.println("\n--- Cadastro de Cliente ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        Cliente cliente = new Cliente(nome, cpf);
        ClienteDAO clienteDAO = new ClienteDAO(DatabaseUtils.openDatabase());
        clienteDAO.salvar(cliente);
        System.out.println("Cliente cadastrado com sucesso!\n");
    }

	public static void deletarCliente(Scanner scanner) {
        System.out.println("\n--- Realizar Exclusão - Clientes ---");
        
        ClienteDAO clienteDAO = new ClienteDAO(DatabaseUtils.openDatabase());
        List<Cliente> clientes = clienteDAO.listarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
            return;
        }
        
        for( int i=0;i < clientes.size(); i++) {
        	System.out.println((i + 1) +". " + clientes.get(i).getNome() + " CPF: " + clientes.get(i).getCpf());
        }
        
        System.out.println("Selecione o cadastro que deseja excluir: ");
        int opcao;
        try {
        	opcao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException  e) {
        	System.out.println("Opção inválida. Tente novamente.");
        	return;
        }
        
        if (opcao < 1 || opcao > clientes.size()) {
        	System.out.println("Opção fora de intervalo, tente novamente.");
        	return;
        }
        
        Cliente clienteSelecionado = clientes.get(opcao -1);
        System.out.print("Tem certeza de qe deseja excluir o cliente " + clienteSelecionado.getNome() + ", CPF: " + clienteSelecionado.getCpf() + "(s/n): ");
        
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("s")) {
        	clienteDAO.deletar(clienteSelecionado);
        	System.out.println("Cliente excluído com sucesso.");
        } else {
        	System.out.println("Operação cancelada.");
    	}
        
    }
	
}
