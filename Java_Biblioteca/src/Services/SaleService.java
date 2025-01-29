package Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DAO.ClientDAO;
import DAO.BookDAO;
import DAO.SaleDAO;

import Entities.Client;
import Entities.ItemSale;
import Entities.Book;
import Entities.Sale;

import Utils.DatabaseUtils;
import Utils.InputUtils;

public class SaleService {
	private InputUtils inputUtils;
	private ClientDAO clientDAO;
	private BookDAO bookDAO;
	
	public SaleService (InputUtils inputUtils, ClientDAO clienteDAO, BookDAO livroDAO) {
		this.inputUtils = inputUtils;
		this.clientDAO = clienteDAO;
		this.bookDAO = livroDAO;
	}
	
	
	// TODO: Apply clean-code concepts into VendaService;
    public List<Client> createConnectionAndCaptureClients() {
    	System.out.println("\n --- Realize a venda ---");
    	clientDAO = new ClientDAO(DatabaseUtils.openDatabase());
    	List<Client> clientListing = clientDAO.listAll();
    	if (clientListing.isEmpty()) {
    		System.out.println("Clientes nao disponiveis.");
    	} else {
    		for (int i = 0; i < clientListing.size(); i++) {
    			System.out.println((i + 1) + ". " + clientListing.get(i).getName() + " (CPF: " + clientListing.get(i).getCpf() + ")");
    		}
    	}

    	return clientListing;
    }
    
    
    
    public Client collectValueFromClienteList(List<Client> clientListing) {
    	int clientMenuIndexBasedSellect = inputUtils.integerInput("Escolha o número Identificador do Cliente: ");
    	Client selectedClient = clientListing.get(clientMenuIndexBasedSellect);
    	return selectedClient;
    }
    
    
    public List<Book> selectBookFromListToSell(BookDAO bookDAO) {
    	
    	bookDAO = new BookDAO(DatabaseUtils.openDatabase());
    	List<Book> booksListing = bookDAO.listAll();
    	
    	if (booksListing.isEmpty()) {
            System.out.println("Não há livros cadastrados para realizar a venda.");
    	} else {
    		System.out.println("Selecione os livros para a venda: ");
        	for (int i = 0; i < booksListing.size(); i++) {
        		System.out.println((i + 1) + booksListing.get(i).getTitle() + " - Preço: R$ " + booksListing.get(i).getPrice());
        	}
    	}
    	
    	return booksListing;
    }
	
	/**
	 * 
	 * TODO Corrige essa merda aqui.
	 * 
	 * :P
	 * 
	 * */
    public void realizarVenda() {
        
        
        bookDAO = new BookDAO(DatabaseUtils.openDatabase());
        List<Book> books = bookDAO.listAll();
        
        if (books.isEmpty()) {
            System.out.println("Não há livros cadastrados para realizar a venda.");
            return;
        }

        System.out.println("Selecione os livros para a venda:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i).getTitle() + " - Preço: R$" + books.get(i).getPrice());
        }
        List<ItemSale> saleItems = new ArrayList<>();
        boolean addMoreBooksToSale = true;
        while (addMoreBooksToSale) {
            System.out.print("Escolha o número do livro para adicionar à venda: ");
            int bookIndex = inputUtils.integerInput("") - 1;            
            int bookQuantity = inputUtils.integerInput("Quantos exemplares você deseja adquirir? ");
            Book selectedBookByIndex = books.get(bookIndex);
            ItemSale cart = new ItemSale(selectedBookByIndex, bookQuantity);
            saleItems.add(cart);
            System.out.print("Deseja adicionar outro livro? (s/n): ");
            Boolean buyResponse = inputUtils.booleanInput("Deseja adicionar mais algum produto? (s/n)");
            if (!buyResponse.equals(true)) {
                addMoreBooksToSale = false;
            }
        }
        
        
        // Well done lil nigga.
        Date confirmedSale = new Date();
        
        // for-loop response
        Sale sale = new Sale(saleItems, selectedClient, confirmedSale);
        System.out.println("\n--- Venda Realizada com Sucesso ---");
        System.out.println("Cliente: " + selectedClient.getName());
        System.out.println("Total da venda: R$ " + sale.calculateTotalSaleValue());

        SaleDAO saleDAO = new SaleDAO(DatabaseUtils.openDatabase());
        saleDAO.save(sale);
        System.out.println("Venda registrada no sistema!\n");
    }
}
