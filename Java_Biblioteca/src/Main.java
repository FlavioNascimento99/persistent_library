
import java.util.Scanner;

import javax.persistence.EntityManager;

import DAO.ClientDAO;
import DAO.BookDAO;

import Interfaces.InputOutputInterface;

import Services.ClientService;
import Services.BookService;
import Services.SaleService;

import Utils.Database;
import Utils.Input;

public class Main {
    public static void main(String[] args) {
    	Scanner scannerAtMain = new Scanner(System.in);
    	Input inputUtils = new Input(scannerAtMain);
    	
    	var data = Database.openConnection();
   		BookDAO bookDAO  = new BookDAO(data);
   		ClientDAO clientDAO = new ClientDAO(data);
  	
    	BookService bookService = new BookService(bookDAO, inputUtils);
    	ClientService clientService = new ClientService(clientDAO, inputUtils);
    	SaleService saleService = new SaleService(inputUtils, clientDAO, bookDAO);

    	InputOutputInterface inputOutputInterface = new InputOutputInterface(clientService, bookService, saleService, inputUtils);
   	
    	 // Exibir Menu Principal
       inputOutputInterface.InterfacePrinter();


        scannerAtMain.close();

    }
}
