package Interfaces;

import Services.ClienteService;
import Services.LivroService;
import Services.VendaService;

import Utils.InputUtils;

public class InputOutputInterface {
	private ClienteService clienteService;
	private LivroService livroService;
	private VendaService vendaService;
	private InputUtils inputUtils;
	
	public InputOutputInterface(ClienteService clienteService, LivroService livroService, VendaService vendaService, InputUtils inputUtils) {
		this.clienteService = clienteService;
		this.livroService = livroService;
		this.vendaService = vendaService;
		this.inputUtils = inputUtils;
	}
	
	public void InterfacePrinter() {
		
	    while (true) {
	        System.out.println("=====================================");
	        System.out.println("||      Sistema de Livraria        ||");
	        System.out.println("=====================================");
	        System.out.println("1. Livro.Cadastro");
	        System.out.println("2. Livro.Listagem");
	        System.out.println("3. Livro.Busca");
	        System.out.println("4. Cliente.Cadastro");
	        System.out.println("5. Cliente.Listagem");
	        System.out.println("6. Cliente.Exclusão");
	        System.out.println("7. Venda.Cadastrar");
	        System.out.println("8. * Fechar *");
	        
	        int opcao = inputUtils.confirmMenuSelection("O que deseja: ", 1, 8);
	        switch (opcao) {
	            case 1:
	            	livroService.cadastrarLivro();
	                break;
	            case 2:
	                livroService.listarLivros();
	                break;
	            case 3:
	                livroService.buscarLivroPorTitulo();
	                break;
	            case 4:
	                clienteService.cadastrarCliente();
	                break;
	            case 5:
	                clienteService.listarClientes();
	                break;
	            case 6:
	            	clienteService.deletarCliente();
	            	break;
	            case 7:
	                vendaService.realizarVenda(); // To-do
	                break;
	            case 8:
	                System.out.println("Saindo do sistema...");
	                return;
	            default:
	                System.out.println("Opção inválida. Tente novamente.");
	        }
	    }
	    
	}
}