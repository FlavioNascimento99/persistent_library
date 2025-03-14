package Services;

import java.util.List;

import javax.persistence.EntityManager;

import DAO.BookDAO;
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
	public BookService(EntityManager manager, Input input) {
		this.input = input;
		this.manager = manager;
		this.bookDAO = new BookDAO(manager);
	}


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
	public void create() {
		try {
			logger.info("Iniciando módulo de cadastro de Livros...");
			Database.beginTransaction(manager);

			System.out.println("\n--- Cadastro de Livro ---");
			String title = input.stringInput("Titulo: ");

			System.out.println("Manager from Service:" + manager);
			String author = input.stringInput("Autor: ");
			Double price = input.doubleInput("Valor do Produto: ");
			String isbn = input.stringInput("ISBN: ");

			if (bookDAO.isFieldExists("isbn", isbn)) {
				System.out.println("Código do livro já se encontra cadastrado.");
				return;
			}

			Book book = new Book(title, author, price, isbn);

			bookDAO.save(book);
			Database.commitTransaction(manager);  // Commit da transação

			System.out.println("Livro cadastrado com sucesso!\n");

		} catch (Exception e) {
			Database.rollbackTransaction(manager);  // Rollback da transação em caso de erro
			System.out.println(e.getMessage());
		}
	}




	// Método de exclusão de Livros
	public void delete() {
		Database.beginTransaction(manager);

		System.out.println("\n--- Exclui do livro ---");
		List<Book>bookList = bookDAO.list();

		try {
			if (bookList.isEmpty()) {
				System.out.println("Não existem livros cadastrados.");
			} else {
				for (int d = 0; d < bookList.size(); d++) {
					System.out.println((d + 1) + ". " + bookList.get(d).getTitle());
				}

				int option = input.integerInput("Selecione um livro: ");
				if(option < 1 || option > bookList.size()) {
					System.out.println("Opcao fora de intervalo, tente novamente.");
					return;
				}

				Book selectedBook = bookList.get(option - 1);
				String confirmation = input.stringInput("Voce deseja remover " + selectedBook.getTitle() + " (s/n)? ");
				if (confirmation.equalsIgnoreCase("s")) {
					bookDAO.delete(selectedBook);
					Database.commitTransaction(manager);
					System.out.println("Livro removido com sucesso!\n");
				} else {
					System.out.println("Operacao cancelada");
				}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			Database.rollbackTransaction(manager);
		}

	}

	
	
	// Método de edição de livros 
	public void update() {
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
	public void search() {
    	try {
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
			System.out.println(e.getMessage());
    		Database.rollbackTransaction(manager);
    	}
    }



	// Listagem de livros mais vendidos.
	public void mostSold() {
		System.out.println("\n--- Livros mais vendidos ---");
		if(bookDAO.bestSellers().isEmpty()){
			System.out.println("Não foi encontrado nenhum titulo");
		} else {
			for (Book book : bookDAO.bestSellers()) {
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