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

import javax.persistence.EntityManager;

public class SaleService {
	private final Input inputUtils;
	private final BookDAO bookDAO;
	private final ClientDAO clientDAO;
	private EntityManager manager;

	public SaleService(Input inputUtils, ClientDAO clientDAO, BookDAO bookDAO) {
		this.inputUtils = inputUtils;
		this.clientDAO = clientDAO;
		this.bookDAO = bookDAO;
	}

	public Sale processSale() {

		manager = Database.openConnection();
		List<Client> clientListing = clientDAO.list();
		if (clientListing.isEmpty()) {
			System.out.println("Não foi encontrado nenhum cliente registrado.");
			Database.closeConnection(manager);
			return null;
		}
		System.out.println("\n--- Clientes Disponíveis ---");
		for (int i = 0; i < clientListing.size(); i++) {
			System.out.println((i + 1) + ". " + clientListing.get(i).getName()
					+ " (Id: " + clientListing.get(i).getId() + ")");
		}
		int clientSelection = inputUtils.integerInput("Selecione o cliente que deseja efetuar venda: ");
		if (clientSelection < 1 || clientSelection > clientListing.size()) {
			System.out.println("Número passado excede a quantidade existente em listagem.");
			Database.closeConnection(manager);
			return null;
		}


		Client selectedClient = clientListing.get(clientSelection - 1);
		Database.closeConnection(manager);

		// 2. Seleciona os livros disponíveis
		manager = Database.openConnection();
		List<Book> booksListing = bookDAO.list();
		if (booksListing.isEmpty()) {
			System.out.println("Não há livros cadastrados para realizar a venda.");
			Database.closeConnection(manager);
			return null;
		}


		System.out.println("\n--- Livros Disponíveis ---");
		for (int i = 0; i < booksListing.size(); i++) {
			System.out.println((i + 1) + ". " + booksListing.get(i).getTitle()
					+ " - Preço: R$ " + booksListing.get(i).getPrice());
		}
		Database.closeConnection(manager);


		// 3. Adiciona itens à venda
		List<ItemSale> saleItems = new ArrayList<>();
		boolean addMoreBooks = true;
		while (addMoreBooks) {
			int bookIndex = inputUtils.integerInput("Escolha o livro desejado para venda: ") - 1;
			if (bookIndex < 0 || bookIndex >= booksListing.size()) {
				System.out.println("Número passado excede a quantidade existente em listagem.");
				break;
			}
			int bookQuantity = inputUtils.integerInput("Quantos exemplares você deseja adquirir? ");
			Book selectedBook = booksListing.get(bookIndex);


			// Cria o item e ainda não associa a venda (será feito abaixo)
			ItemSale item = new ItemSale(selectedBook, bookQuantity);
			saleItems.add(item);
			Boolean response = inputUtils.booleanInput("Deseja adicionar mais algum produto? (s/n)");
			if (!response) {
				addMoreBooks = false;
			}
		}

		if (saleItems.isEmpty()) {
			System.out.println("Nenhum item foi adicionado ao carrinho.");
			return null;
		}


		// 4. Confirma e persiste a venda com seus itens
		manager = Database.openConnection();

		try {

			manager.getTransaction().begin();
			Date saleDate = new Date();


			// Cria a venda associando os itens e o cliente
			Sale sale = new Sale(saleItems, selectedClient, saleDate);


			// Para cada item, associa a venda (para preencher a FK sale_id)
			for (ItemSale item : saleItems) {
				item.setSale(sale);
			}

			selectedClient.addSale(sale);


			System.out.println("Venda efetivada com sucesso - Total da venda: R$ "
					+ sale.calculateTotalSaleValue());

			SaleDAO saleDAO = new SaleDAO(manager);
			saleDAO.save(sale, manager);

			manager.getTransaction().commit();
			System.out.println("Venda realizada com sucesso.");

			return sale;

		}

		catch (Exception e) {

			manager.getTransaction().rollback();
			System.out.println("Erro ao realizar venda: " + e.getMessage());
			return null;

		} finally {

			Database.closeConnection(manager);

		}
	}

}
