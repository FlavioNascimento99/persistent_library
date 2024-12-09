package Services;

import java.util.Scanner;

import DAO.LivroDAO;
import Entities.Livro;
import Utils.Util;

public class LivroService {
	
	// Listagem de Livros
    private static void listarLivros() {
        System.out.println("\n--- Lista de Livros ---");
        // Instanciação do Data Access de Livro, novamente fazendo abertura do Banco para consulta.
        LivroDAO livroDAO = new LivroDAO(Util.openDatabase());
        // Laço de leitura de dados dentro
        for (Livro livro : livroDAO.listarTodos()) {
            System.out.println(livro);
        }
        System.out.println();
    }
	
	// Cadastro de Livros
    private static void cadastrarLivro(Scanner scanner) {
        System.out.println("\n--- Cadastro de Livro ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        // Instanciação da Entidade e do Data Access de Livro, esse segundo com objetivo de abrir a conexão com Banco.
        Livro livro = new Livro(titulo, autor, preco);
        LivroDAO livroDAO = new LivroDAO(Util.openDatabase());
        // Uso da regra de negócio "salvar" no Data Access, para salvar objeto Livro instanciado.
        livroDAO.salvar(livro);
        System.out.println("Livro cadastrado com sucesso!\n");
    }

    // Buscar Livro por Titulo
    private static void buscarLivroPorTitulo(Scanner scanner) {
        System.out.println("\n--- Buscar Livro por Título ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        // Instanciação do DataAccess chamando método de conexão com Banco de Dados
        LivroDAO livroDAO = new LivroDAO(Util.openDatabase());   
        for (Livro livro : livroDAO.buscaPorTitulo(titulo)) {
            System.out.println(livro);
        }
        System.out.println();
    
    }
}
