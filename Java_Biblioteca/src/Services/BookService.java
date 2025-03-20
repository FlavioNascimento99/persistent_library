package Services;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;

import DAO.BookDAOImpl;
import Entities.Book;
import Interfaces.BookDAO;
import Utils.Database;
import org.apache.log4j.Logger;

public class BookService {

	// Attributes
	private static final Logger logger = Logger.getLogger(BookService.class);
	private final BookDAO bookDAO;
	private EntityManager manager;


	// Class-constructor
	public BookService(EntityManager manager) {
		this.manager = manager;
		this.bookDAO = new BookDAOImpl(manager);
	}


	//
	public List<Book> list() {
		Database.beginTransaction(manager);
		List<Book> books = new ArrayList<>();

		for (Book book : bookDAO.list()) {
			books.add(book);
		}
		Database.closeConnection(manager);
		return books;
    }


	//==================================================================//
	//																	//
	//			  Regras de Negócio para cadastro de Livro.				//
	//																	//
	//==================================================================//
	public void create(String isbn, String title, String author, String year, String genre, Double price) {
		try {
			logger.info("Iniciando módulo de cadastro de Livros...");
			Database.beginTransaction(manager);

			// Verifica se o ISBN já existe
			if (bookDAO.isFieldExists("isbn", isbn)) {
				throw new Exception("Código do livro já se encontra cadastrado.");
			}

			// Cria o livro
			Book book = new Book(isbn, title, author, year, genre, price);

			// Salva no banco
			bookDAO.save(book);
			Database.commitTransaction(manager);
			System.out.println("Livro cadastrado com sucesso!\n");

		} catch (Exception e) {
			Database.rollbackTransaction(manager);  // Rollback da transação em caso de erro
			System.out.println(e.getMessage());
		}
	}




	public void delete(String bookTitle) {
		Database.beginTransaction(manager);

		System.out.println("\n--- Exclui do livro ---");
		List<Book>bookList = bookDAO.list();

		Book bookToDelete = null;
		for (Book book : bookList) {
			if (book.getTitle().equalsIgnoreCase(bookTitle)){
				bookToDelete = book;
				break;
			}
		}

		if (bookToDelete == null) {
			System.out.print("Book is null!");
			return;
		}

		bookDAO.delete(bookToDelete);
		Database.commitTransaction(manager);
	}

	
	
	// Método de edição de livros 
	public void update(String oldTitle, String newTitle, String newAuthor, Double newPrice, String newIsbn, String newGenre, String newYear) {
		try {
			Database.beginTransaction(manager);
			Book book = bookDAO.searchByTitle(oldTitle);

			if (book == null) {
				throw new Exception("Não existem livros cadastrados.");
			}

			// Atualiza apenas os valores fornecidos
			if (newTitle != null && !newTitle.isEmpty()) book.setTitle(newTitle);
			if (newAuthor != null && !newAuthor.isEmpty()) book.setAuthor(newAuthor);
			if (newPrice != null && newPrice > 0) book.setPrice(newPrice);
			if (newIsbn != null && !newIsbn.isEmpty()) book.setIsbn(newIsbn);
			if (newGenre != null && !newGenre.isEmpty()) book.setGenre(newGenre);
			if (newYear != null && !newYear.isEmpty()) book.setYear(newYear);

			bookDAO.update(book);
			Database.commitTransaction(manager);
		} catch (Exception e) {
			Database.rollbackTransaction(manager);
			throw new RuntimeException("Erro ao atualizar o livro: " + e.getMessage());
		}
	}



	// Método de busca de livros
	public Book search(String title) throws Exception {
		Book result = bookDAO.searchByTitle(title);
		if (result == null) {
			throw new Exception();
		}
		return result;
    }


	/**
	 *
	 * * CONSULTA * Livros mais vendidos.
	 *
	 */
	public void mostSold() {
		System.out.println("\n--- Livros mais vendidos ---");
		if(bookDAO.listTopSellingBooks().isEmpty()){
			System.out.println("Não foi encontrado nenhum titulo");
		} else {
			for (Book book : bookDAO.listTopSellingBooks()) {
				if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
					System.out.println(
							book.getTitle() + "\n" +
							book.getAuthor() + "\n" +
							"Livro sem ISBN válido" + "\n" +
							book.getPrice() + "\n" +
							book.getQuantitySold());
					System.out.println("\n");
				} else {
					System.out.println(book);
					System.out.println("\n");
				}
			}
		}
	}

}