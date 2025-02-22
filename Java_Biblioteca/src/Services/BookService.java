package Services;

import java.util.List;

import javax.persistence.EntityManager;

import DAO.BookDAO;
import Entities.Book;
import Utils.Database;
import Utils.Input;

public class BookService {
	private Input input;
	private BookDAO bookDAO;
	private EntityManager manager;

	public BookService() {}
	public BookService(BookDAO bookDAO, Input input) {
		this.input = input;
		this.bookDAO = bookDAO;
	}
	
    public void listAll() {
        try {
            manager = Database.openConnection();
            System.out.println("\n--- Lista de Livros ---");
            for (Book book : bookDAO.listAll()) {
                System.out.println(book);
            }
        } finally {
            Database.closeConnection(manager);  // Garante o fechamento no final
        }
    }
	
	public void addBook() {
		try {
			manager = Database.openConnection();
			manager.getTransaction().begin();
			
			System.out.println("\n--- Cadastro de Livro ---");
			
			String title = input.stringInput("Titulo: ");
	        String author = input.stringInput("Autor: ");
	        Double price = input.doubleInput("Valor do Produto: ");
	        Book book = new Book(title, author, price);
	        
	        bookDAO.save(book, manager);
	        
	        manager.getTransaction().commit();	        

	        System.out.println("Livro cadastrado com sucesso!\n");
	        
		}
		catch(Exception e) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();				
			}
			System.out.println(e.getMessage());
		}
		Database.closeConnection(manager);
	}

    public void searchBookByTitle() {
    	try {
        	manager = Database.openConnection();
        	System.out.println("\n--- Buscar Livro por Título ---");
        	
        	String title = input.stringInput("Digite um nome que componha o título do livro: ");
        	List<Book> result = bookDAO.searchByTitle(title);
        	        	
        	if(result.isEmpty()){ 
        		System.out.println("Não foi encontrado nenhum titulo");
        	} else {
        		for (Book book : result) {
        			System.out.println("Resultado da busca: " + book);    			
        		}
        	}
        } catch (Exception e) {
    		manager.getTransaction().rollback();
    		System.out.println(e.getMessage());
    	}
    } 
}
