
import java.util.Scanner;

import DAO.ClientDAO;
import DAO.BookDAO;

import Interfaces.InputOutputInterface;

import Services.ClientService;
import Services.BookService;
import Services.SaleService;

import Utils.DatabaseUtils;
import Utils.InputUtils;

public class Main {
    public static void main(String[] args) {
    	Scanner scannerAtMain = new Scanner(System.in);
    	InputUtils inputUtils = new InputUtils(scannerAtMain);
    	
    	var data = DatabaseUtils.openDatabase();
    	
    	BookDAO livroDAO = new BookDAO(data);
    	ClientDAO clienteDAO = new ClientDAO(data);
    	
    	BookService livroService = new BookService(livroDAO, inputUtils);
    	ClientService clienteService = new ClientService(clienteDAO, inputUtils);
    	SaleService vendaService = new SaleService(inputUtils, clienteDAO, livroDAO);

    	InputOutputInterface inputOutputInterface = new InputOutputInterface(clienteService, livroService, vendaService, inputUtils);
    	
    	 // Exibir Menu Principal
        inputOutputInterface.InterfacePrinter();

        // Fechar conexão com o banco após o uso
        DatabaseUtils.closeDatabase();
        scannerAtMain.close();

    }
}
