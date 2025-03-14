package Interfaces;

import DAO.SaleDAO;
import Services.ClientService;
import Services.BookService;
import Services.SaleService;

import Utils.Database;
import Utils.Input;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class InputOutputInterface {
	private static final Logger logger = Logger.getLogger(InputOutputInterface.class);


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
	        System.out.println("8. Cliente.Editar");
			System.out.println("9. Venda.Cadastrar");
			System.out.println("10 A - CONSULTA: Pesquisar Clientes com gasto Minimo");
			System.out.println("11 B - CONSULTA: Pesquisar Vendas por periodo de tempo");
			System.out.println("12 C - CONSULTA: Pesquisar Livros mais vendidos em unidades");

			System.out.println("0. * Fechar *");
	        
	        int option = input.confirmMenuSelection("O que deseja: ", 0, 11);
	        switch (option) {

				//======================================//
				//			  Book Services				//
				//======================================//
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



				//======================================//
				//			  Book Services				//
				//======================================//
	            case 6:
	                clientService.create();
	                break;
	            case 7:
	                clientService.list();
	                break;
				case 8:
					clientService.update();
					break;




				//======================================//
				//				Sale Service			//
				//======================================//
				case 9:
	                saleService.processSale();
	                break;



				// Consultas Personalizadas
				case 10:
					clientService.getClientByMinimumSpent(500.0);
					break;
				case 11:
					saleService.findSaleByDate();
					break;
				case 12:
					bookService.mostSold();
					break;



				// Ponto de interrupção
	            case 0:
	                System.out.println("Saindo do sistema...");
					Database.shutdown();
					return;
	            default:
	                System.out.println("Opção inválida. Tente novamente.");
	        }
	    }
	    
	}
}