package Interfaces;

import Services.ClientService;
import Services.BookService;
import Services.SaleService;

import Utils.InputUtils;

public class InputOutputInterface {
//	private ClientService clientService;
//	private BookService bookService;
//	private SaleService saleService;
//	private InputUtils inputUtils;
//	
//	public InputOutputInterface(ClientService clientService, BookService bookService, SaleService saleService, InputUtils inputUtils) {
//		this.clientService = clientService;
//		this.bookService = bookService;
//		this.saleService = saleService;
//		this.inputUtils = inputUtils;
//	}
//	
//	public void InterfacePrinter() {
//		
//	    while (true) {
//	        System.out.println("=====================================");
//	        System.out.println("||      Sistema de Livraria        ||");
//	        System.out.println("=====================================");
//	        System.out.println("1. Livro.Cadastro");
//	        System.out.println("2. Livro.Listagem");
//	        System.out.println("3. Livro.Busca");
//	        System.out.println("4. Cliente.Cadastro");
//	        System.out.println("5. Cliente.Listagem");
//	        System.out.println("6. Cliente.Exclusão");
//	        System.out.println("7. Venda.Cadastrar");
//	        System.out.println("8. * Fechar *");
//	        
//	        int opcao = inputUtils.confirmMenuSelection("O que deseja: ", 1, 8);
//	        switch (opcao) {
//	            case 1:
//	            	bookService.addBook();
//	                break;
//	            case 2:
//	                bookService.listAll();
//	                break;
//	            case 3:
//	                bookService.searchBookByTitle();
//	                break;
//	            case 4:
//	                clientService.addClient();
//	                break;
//	            case 5:
//	                clientService.listAll();
//	                break;
//	            case 6:
//	            	clientService.deleteClient();
//	            	break;
//	            case 7:
//	                saleService.finallySale();
//	                break;
//	            case 8:
//	                System.out.println("Saindo do sistema...");
//	                return;
//	            default:
//	                System.out.println("Opção inválida. Tente novamente.");
//	        }
//	    }
//	    
//	}
}