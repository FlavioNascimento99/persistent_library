package Services;

import java.util.List;

import javax.persistence.EntityManager;

import DAO.BookDAO;
import DAO.SaleDAO;
import Entities.Book;
import Utils.Database;
import Utils.Input;
import org.apache.log4j.Logger;

public class BookService {
	private static final Logger logger = Logger.getLogger(BookService.class);
	private final Input input;
	private final BookDAO bookDAO;
	private EntityManager manager;



	// Constructor Default
	public BookService(BookDAO bookDAO, Input input) {
		this.input = input;
		this.bookDAO = bookDAO;
	}




	// Método de listagem de Livros
    public void list() {
        try {
			logger.info("Inicializando busca de livros no Banco de Dados...");
            manager = Database.openConnection();
            System.out.println("\n--- Lista de Livros ---");
            for (Book book : bookDAO.list()) {
                System.out.println(book);
            }
        } finally {
          	Database.closeConnection(manager);
        }
    }



	// Método de criação de Livros
	public void create() {
		try {
			logger.info("Iniciando módulo de cadastro de Livros...");

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

		} catch(Exception e) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			Database.closeConnection(manager);
		}
	}



	// Método de exclusão de Livros
	public void delete() {
		manager = Database.openConnection();
		manager.getTransaction().begin();

		System.out.println("\n--- Exclui do livro ---");
		List<Book>bookList = bookDAO.list();

		try {
			if (bookList.isEmpty()) {
				System.out.println("Não existem livros cadastrados.");
			} else {
				for (int d = 0; d < bookList.size(); d++) {
					System.out.println((d + 1) + ". " + bookList.get(d).getTitle());
				}
				int option;
				option = input.integerInput("Selecione um livro: ");
				if(option < 1 || option > bookList.size()) {
					System.out.println("Opcao fora de intervalo, tente novamente.");
					return;
				}

				Book selectedBook = bookList.get(option - 1);
				String confirmation = input.stringInput("Voce deseja remover " + selectedBook.getTitle() + " (s/n)? ");
				if (confirmation.equalsIgnoreCase("s")) {
					Book managedBook = manager.merge(selectedBook);
					bookDAO.delete(managedBook, manager);
					manager.getTransaction().commit();
					System.out.println("Livro removido com sucesso!\n");
				} else {
					System.out.println("Operacao cancelada");
				}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			manager.getTransaction().rollback();
		} finally {
			Database.closeConnection(manager);
		}

	}

	
	
	// Método de edição de livros 
	public void update() {
		manager = Database.openConnection();
		manager.getTransaction().begin();

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
				String setNewName = input.stringInput("Deseja alterar o titulo do livro [pressione N para manter o atual]? Titulo atual: " + selectedBook.getTitle());

				if (setNewName.equals("n")  ||  setNewName.equals("N")) {
					System.out.println("Operacao cancelada, titulo sera mantido.");
				} else {
					selectedBook.setTitle(setNewName);
					System.out.println("Titulo atual: " + selectedBook.getTitle());
					bookDAO.update(selectedBook, manager);
				}

				String setNewAuthor = input.stringInput("Deseja alterar o autor do livro [pressione N para manter o atual]? Autor atual: " + selectedBook.getAuthor());
				if (setNewAuthor.equals("n")  ||  setNewAuthor.equals("N")) {
					System.out.println("Operacao cancelada, autor sera mantido.");
				} else {
					selectedBook.setAuthor(setNewAuthor);
					bookDAO.update(selectedBook, manager);
				}

				double setNewPrice = input.doubleInput("Deseja alterar o valor unitario do livro [Mantenha vazio para manter valor]? Valor atual: " + selectedBook.getPrice());
				if (setNewPrice == 0.0) {
					System.out.println("Operacao cancelada, valor sera mantido.");
				} else {
					selectedBook.setPrice(setNewPrice);
					bookDAO.update(selectedBook, manager);
				}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			manager.getTransaction().rollback();
		} finally {
			manager.getTransaction().commit();
			Database.closeConnection(manager);
		}
	}



	// Método de busca de livros
	public void search() {
    	try {
        	manager = Database.openConnection();
        	System.out.println("\n--- Buscar Livro por Título ---");

        	String title = input.stringInput("Digite um nome que componha o título do livro: ");
        	List<Book> result = bookDAO.search(title);
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



	// Listagem de livros mais vendidos.
	public void mostSold() {
		manager = Database.openConnection();
		System.out.println("\n--- Livros mais vendidos ---");
		if(bookDAO.bestSellers().isEmpty()){
			System.out.println("Não foi encontrado nenhum titulo");
		} else {
			for (Book book : bookDAO.bestSellers()) {
				System.out.println(book);
			}
		}
	}

}