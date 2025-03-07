
import java.util.Scanner;

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

    	var data = Database.openConnection();

   		BookDAO bookDAO  = new BookDAO(data);
   		ClientDAO clientDAO = new ClientDAO(data);
		SaleDAO saleDAO = new SaleDAO(data);
  	
    	BookService bookService = new BookService(bookDAO, inputUtils);
    	ClientService clientService = new ClientService(clientDAO, inputUtils);
    	SaleService saleService = new SaleService(inputUtils, clientDAO, bookDAO, saleDAO);

    	InputOutputInterface inputOutputInterface = new InputOutputInterface(clientService, bookService, saleService, inputUtils);
        inputOutputInterface.InterfacePrinter();

        scannerAtMain.close();

    }

}
