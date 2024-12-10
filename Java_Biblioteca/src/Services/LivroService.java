package Services;

import java.util.Scanner;

import DAO.LivroDAO;
import Entities.Livro;
import Utils.DatabaseUtils;
import Utils.InputUtils;

public class LivroService {
	private InputUtils inputUtils;
	private LivroDAO livroDAO;

    public LivroService(LivroDAO livroDAO, InputUtils inputUtils) {
        this.livroDAO = livroDAO;
        this.inputUtils = inputUtils;
    }
	
    public void listarLivros() {
    	
        System.out.println("\n--- Lista de Livros ---");
        
        livroDAO = new LivroDAO(DatabaseUtils.openDatabase());
        
        for (Livro livro : livroDAO.listarTodos()) {
            System.out.println(livro);
        }
        
    }
	
	public void cadastrarLivro() {
		
        System.out.println("\n--- Cadastro de Livro ---");
        
        String titulo = inputUtils.stringInput("Titulo: ");
        String autor = inputUtils.stringInput("Autor: ");
        Double valor = inputUtils.doubleInput("Valor do Produto: ");
        
        Livro livro = new Livro(titulo, autor, valor);
        
        livroDAO.salvar(livro);
        
        System.out.println("Livro cadastrado com sucesso!\n");
    }

    public void buscarLivroPorTitulo() {
    	
        System.out.println("\n--- Buscar Livro por Título ---");
        
        String titulo = inputUtils.stringInput("Digite o nome do livro que deseja encontrar: ");
        
        LivroDAO livroDAO = new LivroDAO(DatabaseUtils.openDatabase());   
        
        for (Livro livro : livroDAO.buscaPorTitulo(titulo)) {
            System.out.println(livro);
        }
        System.out.println();
    
    }
}
