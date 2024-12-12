package Services;

import java.util.ArrayList;
import java.util.List;

import DAO.ClienteDAO;
import DAO.LivroDAO;
import DAO.VendaDAO;
import Entities.Cliente;
import Entities.ItemVenda;
import Entities.Livro;
import Entities.Venda;
import Utils.DatabaseUtils;
import Utils.InputUtils;

public class VendaService {
	private InputUtils inputUtils;
	private ClienteDAO clienteDAO;
	private LivroDAO livroDAO;
	
	public VendaService (InputUtils inputUtils, ClienteDAO clienteDAO, LivroDAO livroDAO) {
		this.inputUtils = inputUtils;
		this.clienteDAO = clienteDAO;
		this.livroDAO = livroDAO;
	}
	
    public void realizarVenda() {
        System.out.println("\n--- Realizar Venda ---");

        
        // Consulta de Clientes dentro do Banco de Dados.
        clienteDAO = new ClienteDAO(DatabaseUtils.openDatabase());
        List<Cliente> clientes = clienteDAO.listarTodos();
         
        // Se listagem estiver vazia, feedback visual é feito a partir de um printLn;
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados para realizar a venda.");
            return;
        }
        
        // Listagem e identificação por meio de Index + 1, para manter a identificação iniando por 1, invés de 0;
        System.out.println("Selecione o cliente para a venda:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).getNome() + " (CPF: " + clientes.get(i).getCpf() + ")");
        }
        
        // Captura do objeto Cliente dentro da listagem;
        int clienteIndex = inputUtils.integerInput("Escolha o número Identificador do Cliente: ");
        Cliente clienteSelecionado = clientes.get(clienteIndex);
        
        
        
        // Consulta de Livros dentro do Banco de Dados.
        livroDAO = new LivroDAO(DatabaseUtils.openDatabase());
        List<Livro> livros = livroDAO.listarTodos();
        
        // Se listagem estiver vazia, feedback visual é feito a partir de um PrintLn;
        if (livros.isEmpty()) {
            System.out.println("Não há livros cadastrados para realizar a venda.");
            return;
        }

        // Novamente feita a listagem por meio do índicie do Objeto + 1; 
        System.out.println("Selecione os livros para a venda:");
        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i + 1) + ". " + livros.get(i).getTitulo() + " - Preço: R$" + livros.get(i).getPreco());
        }

        
        // Criação da lista de itens dentro da venda;
        List<ItemVenda> itensVenda = new ArrayList<>();
        
        
        // Booleano utilizado caso o cliente deseje adicionar mais de um Objeto Livro dentro da sua compra;
        boolean adicionarOutro = true;
        
        // Enquanto o booleano anterior for True, adiciona novo livro a compra.
        while (adicionarOutro) {
            System.out.print("Escolha o número do livro para adicionar à venda: ");
            int livroIndex = inputUtils.integerInput("") - 1;            
            int livroQuantidade = inputUtils.integerInput("Quantos exemplares você deseja adquirir? ");
            
            Livro livroSelecionadoByIndex = livros.get(livroIndex);
            
            ItemVenda carrinho = new ItemVenda(livroSelecionadoByIndex, livroQuantidade);
            
            itensVenda.add(carrinho);

            System.out.print("Deseja adicionar outro livro? (s/n): ");
            Boolean respostaCompra = inputUtils.booleanInput("Deseja adicionar mais algum produto? (s/n)");
            
            if (!respostaCompra.equals(true)) {
                adicionarOutro = false;
            }
        }
        
        Venda venda = new Venda(itensVenda, clienteSelecionado);
        System.out.println("\n--- Venda Realizada com Sucesso ---");
        System.out.println("Cliente: " + clienteSelecionado.getNome());
        System.out.println("Total da venda: R$" + venda.calcularValorTotal());

        VendaDAO vendaDAO = new VendaDAO(DatabaseUtils.openDatabase());
        vendaDAO.salvar(venda);
        System.out.println("Venda registrada no sistema!\n");
    }
}
