import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import DAO.ClienteDAO;
import DAO.LivroDAO;
import DAO.VendaDAO;
import Entities.Cliente;
import Entities.ItemVenda;
import Entities.Livro;
import Entities.Venda;
import Util.Util;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
		Locale.setDefault(Locale.US);
        boolean running = true;

//        ** Sistema de Livraria **
//        Funções padrão de CRUD, aplicando conceitos de persistência de dados dentro do DB4o,
//        Banco de Dados embarcado e orientado à objetos.
        
//        To-Do: toString para Entidades, formatar saída de dados do Objeto. 
        
        
        while (running) {
            System.out.println("=====================================");
            System.out.println("||      Sistema de Livraria        ||");
            System.out.println("=====================================");
            System.out.println("1. Livro - Cadastro");
            System.out.println("2. Livro - Listagem");
            System.out.println("3. Livro - Busca por Título");
            System.out.println("4. Cliente - Cadastro");
            System.out.println("5. Cliente - Listagem");
            System.out.println("6. Cliente - Exclusão");
            System.out.println("7. Realizar Venda!");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha do buffer

            switch (opcao) {
                case 1:
                    cadastrarLivro(scanner);
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    buscarLivroPorTitulo(scanner);
                    break;
                case 4:
                    cadastrarCliente(scanner);
                    break;
                case 5:
                    listarClientes();
                    break;
                case 6:
                	realizarExclusao(scanner);
                	break;
                case 7:
                    realizarVenda(scanner);
                    break;
                case 8:
                    System.out.println("Saindo do sistema...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
        Util.closeDatabase(); // Garantir fechamento do banco de dados
    }
    
    // Método privado de Cadastro de Livros
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

    // Método privado de listagem de Livros
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
    
    // Método privado de busca baseado no título do Objeto.
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

    private static void cadastrarCliente(Scanner scanner) {
        System.out.println("\n--- Cadastro de Cliente ---");
        System.out.print("Nome: ");
       
        String nome = scanner.nextLine();
        
        System.out.print("CPF: ");
        
        String cpf = scanner.nextLine();

        Cliente cliente = new Cliente(nome, cpf);
        ClienteDAO clienteDAO = new ClienteDAO(Util.openDatabase());
        
        clienteDAO.salvar(cliente);

        System.out.println("Cliente cadastrado com sucesso!\n");
    
    }

    private static void listarClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        ClienteDAO clienteDAO = new ClienteDAO(Util.openDatabase());
        for (Cliente cliente : clienteDAO.listarTodos()) {
            System.out.println(cliente);
        }
        System.out.println();
    }

    private static void realizarVenda(Scanner scanner) {
        System.out.println("\n--- Realizar Venda ---");

        // Listar clientes
        ClienteDAO clienteDAO = new ClienteDAO(Util.openDatabase());
        List<Cliente> clientes = clienteDAO.listarTodos();
        
        // Verificar listagem, caso vazia, retorna mensagem a seguir.
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados para realizar a venda.");
            return;
        }

        System.out.println("Selecione o cliente para a venda:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).getNome() + " (CPF: " + clientes.get(i).getCpf() + ")");
        }
        System.out.print("Escolha o número do cliente: ");
        int clienteIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consumir a quebra de linha do buffer
        Cliente clienteSelecionado = clientes.get(clienteIndex);

        // Listar livros disponíveis
        LivroDAO livroDAO = new LivroDAO(Util.openDatabase());
        List<Livro> livros = livroDAO.listarTodos();
        if (livros.isEmpty()) {
            System.out.println("Não há livros cadastrados para realizar a venda.");
            return;
        }

        System.out.println("Selecione os livros para a venda:");
        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i + 1) + ". " + livros.get(i).getTitulo() + " - Preço: R$" + livros.get(i).getPreco());
        }

        // Seleção de livros e quantidades
        List<ItemVenda> itensVenda = new ArrayList<>();
        boolean adicionarOutro = true;

        while (adicionarOutro) {
            System.out.print("Escolha o número do livro para adicionar à venda: ");
            int livroIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Consumir a quebra de linha do buffer

            System.out.print("Quantos exemplares você deseja comprar? ");
            int quantidade = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha do buffer

            Livro livroSelecionado = livros.get(livroIndex);
            ItemVenda item = new ItemVenda(livroSelecionado, quantidade);
            itensVenda.add(item);

            System.out.print("Deseja adicionar outro livro? (s/n): ");
            String resposta = scanner.nextLine().toLowerCase();
            if (!resposta.equals("s")) {
                adicionarOutro = false;
            }
        }
        
        

        // Criar a venda
        Venda venda = new Venda(itensVenda, clienteSelecionado);
        System.out.println("\n--- Venda Realizada com Sucesso ---");
        System.out.println("Cliente: " + clienteSelecionado.getNome());
        System.out.println("Total da venda: R$" + venda.getValorTotal());

        // Salvar a venda no banco de dados
        VendaDAO vendaDAO = new VendaDAO(Util.openDatabase());
        vendaDAO.salvar(venda);
        System.out.println("Venda registrada no sistema!\n");
    }
    
    // Realizar exclusão
    public static void realizarExclusao(Scanner scanner) {
        System.out.println("\n--- Realizar Venda ---");

        // Listar clientes
        ClienteDAO clienteDAO = new ClienteDAO(Util.openDatabase());
        List<Cliente> clientes = clienteDAO.listarTodos();

        
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados para realizar a venda.");
            return;
        }
        
        for( int i=0;i < clientes.size(); i++) {
        	System.out.println((i + 1) +". " + clientes.get(i).getNome() + " CPF: " + clientes.get(i).getCpf());
        }
        
        // Captura a seleção do usuário
        System.out.println("Selecione o cliente que deseja excluir: ");
        int opcao;
        try {
        	opcao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException  e) {
        	System.out.println("Opção inválida. Tente novamente.");
        	return;
        }
        
        // Validação
        if (opcao < 1 || opcao > clientes.size()) {
        	System.out.println("Opção fora de intervalo, tente novamente.");
        	return;
        }
        
        // Captura de cliente subtraindo valor por 1 por conta da indexação da lista
        Cliente clienteSelecionado = clientes.get(opcao -1);
    
        // Confirmar exclusão
        System.out.print("Tem certeza de qe deseja excluir o cliente " + clienteSelecionado.getNome() + ", CPF: " + clienteSelecionado.getCpf() + "(s/n): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("s")) {
        	clienteDAO.deletar(clienteSelecionado);
        	System.out.println("Cliente excluído com sucesso.");
        } else {
        	System.out.println("Operação cancelada.");
    	}
        
    }

    public static void listagemPromocional() {
        System.out.println("\n--- Livros em promoção (Abaixo de R$20,00 ---");

        Livro livro;
        // Listar clientes
        LivroDAO livroDAO= new LivroDAO(Util.openDatabase());
        List<Livro> livros = LivroDAO.livrosPromocao(livro);

    }
    
}
