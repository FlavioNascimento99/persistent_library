package Services;

import java.util.List;

import javax.persistence.EntityManager;

import DAO.BookDAO;
import Entities.Book;
import Utils.Database;
import Utils.InputUtils;

public class BookService {
	private InputUtils inputUtils;
	private BookDAO bookDAO;
	private EntityManager manager;

	
    public void listAll() {
    	manager = Database.openConnection();
    	System.out.println("\n--- Lista de Livros ---");
        for (Book book : bookDAO.listAll()) {
            System.out.println(book);
        }
    }
	
	public void addBook() {
		try {
	    	manager = Database.openConnection();
	        System.out.println("\n--- Cadastro de Livro ---");
			manager.getTransaction().begin();
			String title = inputUtils.stringInput("Titulo: ");
	        String author = inputUtils.stringInput("Autor: ");
	        Double price = inputUtils.doubleInput("Valor do Produto: ");
	        Book book = new Book(title, author, price);
	        bookDAO.save(book);
	        System.out.println("Livro cadastrado com sucesso!\n");
		}
		catch(Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e.getMessage());
		}
		Database.closeConnection();
    }

    public void searchBookByTitle() {

    	try {
        	manager = Database.openConnection();
        	System.out.println("\n--- Buscar Livro por Título ---");
        	
        	String title = inputUtils.stringInput("Digite um nome que componha o título do livro: ");
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
