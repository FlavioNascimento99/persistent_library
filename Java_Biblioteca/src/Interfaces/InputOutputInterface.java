package Interfaces;

import Services.ClientService;
import Services.BookService;
import Services.SaleService;

import Utils.Database;
import Utils.Input;

public class InputOutputInterface {
	private ClientService clientService;
	private BookService bookService;
	private SaleService saleService;
	private Input input;
	
	public InputOutputInterface(ClientService clientService, BookService bookService, SaleService saleService, Input input) {
		this.clientService = clientService;
		this.bookService = bookService;
		this.saleService = saleService;
		this.input = input;
	}
	
	public void InterfacePrinter() {
		
	    while (true) {
	        System.out.println("=====================================");
	        System.out.println("||      Sistema de Livraria        ||");
	        System.out.println("=====================================");
	        System.out.println("1. Livro.Cadastro");
	        System.out.println("2. Livro.Listagem");
	        System.out.println("3. Livro.Busca");
			System.out.println("4. Livro.Editar");
			System.out.println("5. Livro.Excluir");
	        System.out.println("6. Cliente.Cadastro");
	        System.out.println("7. Cliente.Listagem");
	        System.out.println("8. Cliente.Atualizar");
	        System.out.println("9. Cliente.Excluir");
			System.out.println("10. Venda.Cadastrar");
			System.out.println("11. * Fechar *");
	        
	        int option = input.confirmMenuSelection("O que deseja: ", 1, 11);
	        switch (option) {
	            case 1:
	            	bookService.create();
	                break;
	            case 2:
	                bookService.list();
	                break;
	            case 3:
	                bookService.search();
	                break;
				case 4:
					bookService.update();
					break;
				case 5:
					bookService.delete();
					break;
	            case 6:
	                clientService.create();
	                break;
	            case 7:
	                clientService.list();
	                break;
				case 8:
					clientService.update();
					break;
	            case 9:
	            	clientService.delete();
	            	break;
	            case 10:
	                saleService.processSale();
	                break;
	            case 11:
	                System.out.println("Saindo do sistema...");
					Database.shutdown();
					return;
	            default:
	                System.out.println("Opção inválida. Tente novamente.");
	        }
	    }
	    
	}
}