package Services;

import java.util.List;
import java.util.Scanner;

import DAO.ClienteDAO;
import Entities.Cliente;


import Utils.DatabaseUtils;
import Utils.InputUtils;

public class ClienteService {
	private InputUtils inputUtils;
	private ClienteDAO clienteDAO;
	
	public ClienteService(InputUtils inputUtils, ClienteDAO clienteDAO) {
		this.inputUtils = inputUtils;
		this.clienteDAO = clienteDAO;
	}
	
	public void listarClientes() {
		
		System.out.println("\n--- Lista de Clientes ---");
		
		ClienteDAO clienteDAO = new ClienteDAO(DatabaseUtils.openDatabase());
		
		for (Cliente cliente : clienteDAO.listarTodos()) {
			System.out.println(cliente);
		}
		System.out.println();
		
	}

	public void cadastrarCliente() {
		
        System.out.println("\n--- Cadastro de Cliente ---");
        
        String nome = inputUtils.stringInput("Nome: ");
        String cpf = inputUtils.stringInput("CPF: ");
        
        Cliente cliente = new Cliente(nome, cpf);
        
        clienteDAO.salvar(cliente);
        
        System.out.println("Cliente cadastrado com sucesso!\n");
        
    }

	public void deletarCliente() {
        
		System.out.println("\n--- Realizar Exclusão - Clientes ---");
        
        clienteDAO = new ClienteDAO(DatabaseUtils.openDatabase());
        
        List<Cliente> clientesLista = clienteDAO.listarTodos();
        
        if (clientesLista.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
            return;
        }
        
        for( int i=0;i < clientesLista.size(); i++) {
        	System.out.println((i + 1) +". " + clientesLista.get(i).getNome() + " CPF: " + clientesLista.get(i).getCpf());
        }
        
        int opcao;
        try {
        	opcao = inputUtils.integerInput("Qual opcao deseja deletar: ");
        } catch (NumberFormatException  e) {
        	System.out.println("Opção inválida. Tente novamente.");
        	return;
        }
        
        if (opcao < 1 || opcao > clientesLista.size()) {
        	System.out.println("Opção fora de intervalo, tente novamente.");
        	return;
        }
        
        Cliente clienteSelecionado = clientesLista.get(opcao -1);
        System.out.print("Tem certeza de qe deseja excluir o cliente " + clienteSelecionado.getNome() + ", CPF: " + clienteSelecionado.getCpf() + "(s/n): ");
        
        String confirmacao = inputUtils.stringInput("");
        if (confirmacao.equalsIgnoreCase("s")) {
        	clienteDAO.deletar(clienteSelecionado);
        	System.out.println("Cliente excluído com sucesso.");
        } else {
        	System.out.println("Operação cancelada.");
    	}
        
    }
	
}
