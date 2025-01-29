package Services;

import java.util.List;

import DAO.BookDAO;
import Entities.Book;
import Utils.DatabaseUtils;
import Utils.InputUtils;

public class BookService {
	private InputUtils inputUtils;
	private BookDAO bookDAO;

    public BookService(BookDAO bookDAO, InputUtils inputUtils) {
        this.inputUtils = inputUtils;
        this.bookDAO = bookDAO;
    }
	
    public void listarLivros() {
        System.out.println("\n--- Lista de Livros ---");
        bookDAO = new BookDAO(DatabaseUtils.openDatabase());
        for (Book book : bookDAO.listAll()) {
            System.out.println(book);
        }
    }
	
	public void addBook() {
        System.out.println("\n--- Cadastro de Livro ---");
        String title = inputUtils.stringInput("Titulo: ");
        String author = inputUtils.stringInput("Autor: ");
        Double price = inputUtils.doubleInput("Valor do Produto: ");
        Book book = new Book(title, author, price);
        bookDAO.save(book);
        System.out.println("Livro cadastrado com sucesso!\n");
    }

    public void searchBookByTitle() {
        System.out.println("\n--- Buscar Livro por Título ---");
        String title = inputUtils.stringInput("Digite o nome do livro que deseja encontrar: ");
        BookDAO bookDAO = new BookDAO(DatabaseUtils.openDatabase());
        List<Book> bookResultList = bookDAO.searchByTitle(title);
    	if(bookResultList.isEmpty()){ 
    		System.out.println("Não foi encontrado nenhum titulo");
    	} else {
    		for (Book book : bookResultList) {
    			System.out.println("Resultado da busca: " + book);    			
    		}
    	}
        
    }
}
