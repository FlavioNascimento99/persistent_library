package Services;

import java.util.List;

import javax.persistence.EntityManager;

import DAO.BookDAOImpl;
import Entities.Book;
import Utils.Database;
import Utils.Input;
import org.apache.log4j.Logger;

public class BookService {

	// Attributes
	private static final Logger logger = Logger.getLogger(BookService.class);
	private final Input input;
	private final BookDAOImpl bookDAO;
	private EntityManager manager;


	// Class-constructor
	public BookService(EntityManager manager, Input input, BookDAOImpl bookDAO) {
		this.input = input;
		this.manager = manager;
		this.bookDAO = bookDAO;
	}


	//
	public void list() {
        try {
			logger.info("Inicializando busca de livros no Banco de Dados...");
			Database.beginTransaction(manager);
            System.out.println("\n--- Lista de Livros ---");

			for (Book book : bookDAO.list()) {
                System.out.println(book);
            }

        } catch(Exception e) {
			System.out.println(e.getMessage());
			Database.rollbackTransaction(manager);
		}
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
	public void update(String oldTitle, String newTitle, String newAuthor, Double newPrice, Double newIsbn) {
		Database.beginTransaction(manager);

		System.out.println("\n--- Editar Livro ---");
		List<Book> bookList = bookDAO.list();

		try {
			if (bookList.isEmpty()) {
				System.out.println("Não existem livros cadastrados.");
			} else {
				for (int u = 0; u < bookList.size(); u++) {
					System.out.println((u + 1) + ". " + bookList.get(u).getTitle());
				}

				int option;
				option = input.integerInput("Selecione um livro: ");

				if (option < 1 || option > bookList.size()) {
					System.out.println("Opcao fora de intervalo, tente novamente.");
					return;
				}


				Book selectedBook = bookList.get(option - 1);
				String setNewName = input.stringInput("[ALTERAR - pressione N e valor será mantido] Titulo(Atual): " + selectedBook.getTitle());

				if (setNewName.equals("n")  ||  setNewName.equals("N")) {
					System.out.println("Sem alteração");
				} else {
					selectedBook.setTitle(setNewName);
					System.out.println("Titulo atual: " + selectedBook.getTitle());
					bookDAO.update(selectedBook);
				}

				String setNewAuthor = input.stringInput("[ALTERAR - Pressione N e o valor será mantido] Autor(Atual): " + selectedBook.getAuthor());
				if (setNewAuthor.equals("n")  ||  setNewAuthor.equals("N")) {
					System.out.println("Sem alteração.");
				} else {
					selectedBook.setAuthor(setNewAuthor);
					bookDAO.update(selectedBook);
				}

				String setNewISBN = input.stringInput("[ALTERAR - Pressione N e o valor será mantido] ISBN(Atual): " + selectedBook.getIsbn());
				if (setNewISBN.equals("n")  ||  setNewISBN.equals("N")) {
					System.out.println("Sem alteração.");
				} else {
					selectedBook.setIsbn(setNewISBN);
					bookDAO.update(selectedBook);
				}

				double setNewPrice = input.doubleInput("[ALTERAR - pressione 0 e o valor será mantido] Valor(Atual): " + selectedBook.getPrice());
				if (setNewPrice == 0.0) {
					System.out.println("Sem alteração.");
				} else {
					selectedBook.setPrice(setNewPrice);
					bookDAO.update(selectedBook);
				}

				Database.commitTransaction(manager);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			Database.rollbackTransaction(manager);
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



	// Listagem de livros mais vendidos.
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