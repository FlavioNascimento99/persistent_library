package Interfaces;

import java.util.Locale;
import java.util.Scanner;

import Services.ClienteService;
import Services.LivroService;
import Services.VendaService;

import Utils.DatabaseUtils;
import Utils.InputUtils;

public class InputOutputInterface {
	private ClienteService clienteService;
	private LivroService livroService;
	private VendaService vendaService;
	
	public InputOutputInterface(ClienteService clienteService, LivroService livroService, VendaService vendaService) {
		this.clienteService = clienteService;
		this.livroService = livroService;
		this.vendaService = vendaService;
	}
	
	public void InterfacePrinter(Scanner scanner) {
		
	    boolean running = false;
	    while (running) {
	        System.out.println("=====================================");
	        System.out.println("||      Sistema de Livraria        ||");
	        System.out.println("=====================================");
	        
	        System.out.println("==== Livros ====");
	        System.out.println("1. Cadastro");
	        System.out.println("2. Listagem");
	        System.out.println("3. Busca");
	        
	        System.out.println("==== Clientes ====");
	        System.out.println("4. Cadastro");
	        System.out.println("5. Listagem");
	        System.out.println("6. Exclusão");
	        
	        System.out.println("==== Vendas ====");
	        System.out.println("7. Realizar Venda!");
	        
	        System.out.println("-------------------");
	        
	        System.out.println("8. Fechar");
	        
	        System.out.println("Escolha uma opção: ");
	
	        int opcao = scanner.nextInt();
	        scanner.nextLine(); // Consumir a quebra de linha do buffer
	
	        switch (opcao) {
	            case 1:
	            	livroService.cadastrarLivro();
	                break;
	            case 2:
	                listarLivros();
	                break;
	            case 3:
	                buscarLivroPorTitulo(scanner);
	                break;
	            case 4:
	                cadastrarCliente(scanner);
	                break;
	            case 5:
	                listarClientes();
	                break;
	            case 6:
	            	realizarExclusao(scanner);
	            	break;
	            case 7:
	                realizarVenda(scanner);
	                break;
	            case 8:
	                System.out.println("Saindo do sistema...");
	                running = false;
	                break;
	            default:
	                System.out.println("Opção inválida. Tente novamente.");
	        }
	
	    scanner.close();
	    Util.closeDatabase(); // Garantir fechamento do banco de dados
	    }
}