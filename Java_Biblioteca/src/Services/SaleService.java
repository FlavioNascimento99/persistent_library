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

import Utils.Database;
import Utils.Input;

public class SaleService {
	private Input inputUtils;
	private BookDAO bookDAO;
	private ClientDAO clientDAO;
	
	public SaleService() {}
	public SaleService(Input inputUtils, ClientDAO clientDAO, BookDAO bookDAO) {
		this.inputUtils = inputUtils;
		this.clientDAO = clientDAO;
		this.bookDAO = bookDAO;
	}
	
	
	
	/**
	 * A list of Clients after make connection with Database
	 * and call function listAll(), store all of it into a List<Client>
	 * 
	 * for-loop will setup Sysout adding +1 just to avoid id=0 into 
	 * list callback.
	 * 
	 * @return List of <Client> called clientListing;
	 * */
    public List<Client> createConnectionAndCaptureClients() {
    	System.out.println("\n --- Realize a venda ---");
    	Database.openConnection();
    	List<Client> clientListing = clientDAO.listAll();
    	if (clientListing.isEmpty()) {
    		System.out.println("Não foi encontrado nenhum cliente registrado.");
    	} else {
    		for (int i = 0; i < clientListing.size(); i++) {
    			System.out.println((i + 1) + ". " + clientListing.get(i).getName() + " (CPF: " + clientListing.get(i).getCpf() + ")");
    		}
    	}
    	return clientListing;
    }
    
    
    
    /**
     * Selecte value into clientListing() Sysout using .get() method 
     * from Java List methods to get index based value with Integer set by user.
     * 
     * @return Selected <Client> from clientListing;
     * */
    public Client collectValueFromClienteList(List<Client> clientListing) {
    	int clientMenuIndexBasedSelect = inputUtils.integerInput("Selecione o cliente que deseja efetuar venda: ");
    	if (clientMenuIndexBasedSelect <= 0 || clientMenuIndexBasedSelect > clientListing.size()) {
    		System.out.println("Número passado excede a quantidade existente em listagem.");
    		return null;
    	} 
    	Client selectedClient = clientListing.get(clientMenuIndexBasedSelect);
    	return selectedClient;
    }
    
    
    
    /**
     * Create conection with Database to get list of dataType <Book>
     * as the same way we do with <Client>, literally the SAME FUCKING THING.
     * 
     * @return List of <Book> who are stored into Database with Sysout.
     * */
    public List<Book> selectBookFromListToSell() {
    	Database.openConnection();
    	List<Book> booksListing = bookDAO.listAll();
    	if (booksListing.isEmpty()) {
            System.out.println("Não há livros cadastrados para realizar a venda.");
    	} else {
    		System.out.println("Selecione os livros para a venda: ");
        	for (int i = 0; i < booksListing.size(); i++) {	
        		System.out.println((i + 1) + ". " + booksListing.get(i).getTitle() + " - Preço: R$ " + booksListing.get(i).getPrice());
        	}
    	}
    	return booksListing;
    }    
    
    
    
    /**
     * If you want to buy more than a single unit of book, you just can select more of them
     * when you do that, we will adding one by one into a list <ItemSale> with the prupose
     * of store a object list of items who you want to buy.
     * 
     * @return The possibility of add more <Book> into a list of <ItemSale> and then returning
     * the second one.
     * */
    public List<ItemSale> addingMoreBooksToSale(List<Book> bookListing) {
    	List<ItemSale> saleItems = new ArrayList<>();
    	boolean addMoreBooksToSale = true;
    	while(addMoreBooksToSale) {
    		System.out.println("Escolha o livro desejado para venda: ");
    		
    		int bookIndex = inputUtils.integerInput("") - 1;
    		
    		if (bookListing.isEmpty()) {
    			System.out.println("Não foram encontrados livros cadastrados.");
    			addMoreBooksToSale = false;
    		}
    		
			if (bookIndex <= 0 || bookIndex > bookListing.size()) {
    			System.out.println("Número passado excede a quantidade existente em listagem.");
    			addMoreBooksToSale = false;
    		}    		
    		
    		int bookQuantity = inputUtils.integerInput("Quantos exemplares você deseja adquirir? ");
    		Book selectedBookFromIndex = bookListing.get(bookIndex);
    		
    		ItemSale cart = new ItemSale(selectedBookFromIndex, bookQuantity);
    		saleItems.add(cart);
            Boolean buyResponse = inputUtils.booleanInput("Deseja adicionar mais algum produto? (s/n)");
            if(!buyResponse.equals(true)) {
            	addMoreBooksToSale = false;
            }
    	}
    	return saleItems;
    }
    
    
    
    /**
     * After all of that stuffs, finally we going to the final setup of this Service,
     * here we confirm the Sale or Buy, whatever, and then add the <Date> object into 
     * <Sale> and saving them into memory before send it to Database.
     * 
     * @return I think's this gonna return an <Sale> object, but i need to check that
     * a moment.
     * */
    public Sale confirmSaleAndDatePrint(List<ItemSale> saleItems, Client selectedClient ) {
    	Date confirmedSale = new Date();
    	SaleDAO saleDAO = new SaleDAO(Database.openConnection());
    	Sale sale = new Sale(saleItems, selectedClient, confirmedSale);
		System.out.println("Venda efeivada com sucesso - Total da venda: R$ " + sale.calculateTotalSaleValue());
        saleDAO.save(sale);   
        System.out.println("Venda realizada com sucesso.");
        return sale;
    }
    
    public Sale finallySale() {
    	List<Client> clients = createConnectionAndCaptureClients();
    	if (clients.isEmpty()) {
    		System.out.println("Listagem de clientes vazia.");
    		return null;
    	}
    	
    	Client clientSelected = null;
    	while(clientSelected == null) {
    		clientSelected = collectValueFromClienteList(clients);
    		if (clientSelected == null) {
    			System.out.println("Seleção inválida, tente novamente.");
    		}
    	}
    	
    	List<Book> books = selectBookFromListToSell();
    	if (books.isEmpty()) {
    		System.out.println("Listagem de livros vazia.");
    		return null;
    	}
    	
    	List<ItemSale> bookSelectedList = addingMoreBooksToSale(books);
    	if (bookSelectedList.isEmpty()) {
    		System.out.println("Nenhum item fora adicionado ao carrinho.");
    		return null;
    	}
    	
		Sale sale = new Sale(bookSelectedList, clientSelected, new Date());
		confirmSaleAndDatePrint(bookSelectedList, clientSelected);
		return sale;
    }
}
