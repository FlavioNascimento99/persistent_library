
import java.util.Scanner;

import DAO.ClienteDAO;
import DAO.LivroDAO;

import Interfaces.InputOutputInterface;

import Services.ClienteService;
import Services.LivroService;
import Services.VendaService;

import Utils.DatabaseUtils;
import Utils.InputUtils;

public class Main {
    public static void main(String[] args) {
    	Scanner scannerAtMain = new Scanner(System.in);
    	InputUtils inputUtils = new InputUtils(scannerAtMain);
    	
    	var data = DatabaseUtils.openDatabase();
    	
    	LivroDAO livroDAO = new LivroDAO(data);
    	ClienteDAO clienteDAO = new ClienteDAO(data);
    	
    	LivroService livroService = new LivroService(livroDAO, inputUtils);
    	ClienteService clienteService = new ClienteService(clienteDAO, inputUtils);
    	VendaService vendaService = new VendaService(inputUtils, clienteDAO, livroDAO);

    	InputOutputInterface inputOutputInterface = new InputOutputInterface(clienteService, livroService, vendaService, inputUtils);
    	
    	 // Exibir Menu Principal
        inputOutputInterface.InterfacePrinter();

        // Fechar conexão com o banco após o uso
        DatabaseUtils.closeDatabase();
        scannerAtMain.close();

    }
}
