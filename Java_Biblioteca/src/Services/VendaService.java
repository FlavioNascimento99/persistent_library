package Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import DAO.ClienteDAO;
import DAO.LivroDAO;
import DAO.VendaDAO;
import Entities.Cliente;
import Entities.ItemVenda;
import Entities.Livro;
import Entities.Venda;
import Utils.Util;

public class VendaService {
	
    private static void realizarVenda(Scanner scanner) {
        System.out.println("\n--- Realizar Venda ---");

        ClienteDAO clienteDAO = new ClienteDAO(Util.openDatabase());
        List<Cliente> clientes = clienteDAO.listarTodos();
        
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
        scanner.nextLine();
        
        Cliente clienteSelecionado = clientes.get(clienteIndex);
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
        
        Venda venda = new Venda(itensVenda, clienteSelecionado);
        System.out.println("\n--- Venda Realizada com Sucesso ---");
        System.out.println("Cliente: " + clienteSelecionado.getNome());
        System.out.println("Total da venda: R$" + venda.getValorTotal());

        VendaDAO vendaDAO = new VendaDAO(Util.openDatabase());
        vendaDAO.salvar(venda);
        System.out.println("Venda registrada no sistema!\n");
    }
}
