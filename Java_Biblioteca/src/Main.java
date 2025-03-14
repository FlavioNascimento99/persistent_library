
import java.util.Scanner;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlAnyAttribute;

import DAO.ClientDAO;
import DAO.BookDAO;

import DAO.SaleDAO;
import Entities.Sale;
import Interfaces.InputOutputInterface;

import Services.ClientService;
import Services.BookService;
import Services.SaleService;

import Utils.Database;
import Utils.Input;
import org.apache.log4j.Logger;

public class Main {

	private static final Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {

    	Scanner scannerAtMain = new Scanner(System.in);
    	Input inputUtils = new Input(scannerAtMain);

		// Abrindo conexão única 😈
		EntityManager manager = Database.getEntityManager();

   		BookDAO bookDAO  = new BookDAO(manager);
   		ClientDAO clientDAO = new ClientDAO(manager);
//		SaleDAO saleDAO = new SaleDAO(manager);

    	BookService bookService = new BookService(manager, inputUtils);
		ClientService clientService = new ClientService(manager, inputUtils);
		SaleService saleService = new SaleService(inputUtils, clientDAO, bookDAO, manager);


		// Inicializador de Interface no CLI
    	InputOutputInterface inputOutputInterface = new InputOutputInterface(clientService, bookService, saleService, inputUtils);
        inputOutputInterface.InterfacePrinter();



		// Finalizar Scanner do Java API
        scannerAtMain.close();

    }

}
