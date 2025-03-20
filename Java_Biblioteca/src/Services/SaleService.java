package Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.ClientDAOImpl;
import DAO.BookDAOImpl;

import DAO.SaleDAOImpl;
import Entities.Client;
import Entities.ItemSale;
import Entities.Book;
import Entities.Sale;

import Utils.Database;
import Utils.Input;
import org.apache.log4j.Logger;

import jakarta.persistence.EntityManager;

public class SaleService {
	private static final Logger logger = Logger.getLogger(SaleService.class);

	private final Input inputUtils;
	private final BookDAOImpl bookDAO;
	private final ClientDAOImpl clientDAO;
	private final SaleDAOImpl saleDAO;
	private EntityManager manager;


	public SaleService(Input inputUtils, ClientDAOImpl clientDAO, BookDAOImpl bookDAO, SaleDAOImpl saleDAO, EntityManager manager) {
		this.manager = manager;
		this.inputUtils = inputUtils;
		this.clientDAO = clientDAO;
		this.bookDAO = bookDAO;
		this.saleDAO = saleDAO;
	}

	public void findSaleByDate() {
		Database.beginTransaction(manager);

		int initialDay = inputUtils.integerInput("[INICIAL] Em que <DIA> foi feita a venda: ");
		int initialMonth = inputUtils.integerInput("[INICIAL] Em que <MÊS> foi feita a venda: ");
		int initialYear = inputUtils.integerInput("[INICIAL] Em que <ANO> foi feita a venda: ");
		LocalDate start = LocalDate.of(initialYear, initialMonth, initialDay);

		int finalDay = inputUtils.integerInput("[FINAL] Em que <DIA> foi feita a venda: ");
		int finalMonth = inputUtils.integerInput("[FINAL] Em que <MÊS> foi feita a venda: ");
		int finalYear = inputUtils.integerInput("[FINAL] Em que <ANO> foi feita a venda: ");
		LocalDate end = LocalDate.of(finalYear, finalMonth, finalDay);


		for (Sale sale: saleDAO.listByDate(start, end)){
			System.out.println(sale.toString());
		}
		Database.commitTransaction(manager);
	}

	public Sale processSale() {

		Database.beginTransaction(manager);
		List<Client> clientListing = clientDAO.list();

		if (clientListing.isEmpty()) {
			System.out.println("Não foi encontrado nenhum cliente registrado.");
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
			return null;
		}

		Client selectedClient = clientListing.get(clientSelection - 1);
		List<Book> booksListing = bookDAO.list();
		if (booksListing.isEmpty()) {
			System.out.println("Não há livros cadastrados para realizar a venda.");
			return null;
		}


		System.out.println("\n--- Livros Disponíveis ---");
		for (int i = 0; i < booksListing.size(); i++) {
			System.out.println((i + 1) + ". " + booksListing.get(i).getTitle()
					+ " - Preço: R$ " + booksListing.get(i).getPrice());
		}

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

			ItemSale item = new ItemSale(selectedBook, bookQuantity);
			saleItems.add(item);
			boolean response = inputUtils.booleanInput("Deseja adicionar mais algum produto? (s/n)");
			if (!response) {
				addMoreBooks = false;
			}
		}

		if (saleItems.isEmpty()) {
			System.out.println("Nenhum item foi adicionado ao carrinho.");
			return null;
		}


		try {
			// Solicita dados para o usuário
			int saleDateDay = inputUtils.integerInput("Em que dia foi feita a venda: ");
			int saleDateMonth = inputUtils.integerInput("Em que mês foi feita a venda: ");
			int saleDateYear = inputUtils.integerInput("Em que ano foi feita a venda: ");

			LocalDate saleDate = LocalDate.now();
			try {
				saleDate = LocalDate.of(saleDateYear, saleDateMonth, saleDateDay);
			} catch (Exception e) {
				saleDate = LocalDate.now();
			}
			System.out.println("Data da venda: " + saleDate);

			Sale sale = new Sale(saleItems, selectedClient, saleDate);
			for (ItemSale item : saleItems) {
				item.setSale(sale);
			}

			selectedClient.addSale(sale);


			System.out.println("Venda efetivada com sucesso - Total da venda: R$ "
					+ sale.calculateTotalSaleValue());

			saleDAO.save(sale);
			Database.commitTransaction(manager);

			System.out.println("Venda realizada com sucesso.");
			return sale;
		} catch (Exception e) {

			Database.rollbackTransaction(manager);
			System.out.println("Erro ao realizar venda: " + e.getMessage());
			return null;

		}
	}

}
