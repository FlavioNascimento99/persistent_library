package Services;

import java.util.Scanner;

import DAO.LivroDAO;
import Entities.Livro;
import Utils.DatabaseUtils;
import Utils.InputUtils;

public class LivroService {
	
	// Listagem de Livros
    private static void listarLivros() {
        System.out.println("\n--- Lista de Livros ---");
        // Instanciação do Data Access de Livro, novamente fazendo abertura do Banco para consulta.
        LivroDAO livroDAO = new LivroDAO(DatabaseUtils.openDatabase());
        // Laço de leitura de dados dentro
        for (Livro livro : livroDAO.listarTodos()) {
            System.out.println(livro);
        }
        System.out.println();
    }
	
	// Cadastro de Livros
    private static void cadastrarLivro(InputUtils inputUtils, LivroDAO livroDAO) {
        System.out.println("\n--- Cadastro de Livro ---");
        
        String titulo = inputUtils.stringInput("Titulo: ");
        String autor = inputUtils.stringInput("Autor: ");
        Double valor = inputUtils.doubleInput("Valor do Produto: ");
        
        // Objeto criado.
        Livro livro = new Livro(titulo, autor, valor);
        
        // DataAccess de Livro salva Objeto Livro instanciado.
        livroDAO.salvar(livro);
        
        // User feedback CLI
        System.out.println("Livro cadastrado com sucesso!\n");
    }

    // Buscar Livro por Titulo
    private static void buscarLivroPorTitulo(Scanner scanner) {
        System.out.println("\n--- Buscar Livro por Título ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        // Instanciação do DataAccess chamando método de conexão com Banco de Dados
        LivroDAO livroDAO = new LivroDAO(DatabaseUtils.openDatabase());   
        for (Livro livro : livroDAO.buscaPorTitulo(titulo)) {
            System.out.println(livro);
        }
        System.out.println();
    
    }
}
