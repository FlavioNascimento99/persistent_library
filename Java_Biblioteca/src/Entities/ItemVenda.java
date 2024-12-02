package Entities;



public class ItemVenda {
	private int id;
	private Livro livro;
	private int quantidade;
    private double precoTotal;       // Preço total do item (livro x quantidade)

	
	ItemVenda(int id, Livro livro, int quantidade) {
		this.id = id;
		this.livro = livro;
		this.quantidade = quantidade;
	}
	
	public Double calcularPrecoTotal() {
		return livro.getPreco() * quantidade;
	}
	
    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
        this.precoTotal = calcularPrecoTotal(); // Recalcula o preço total ao alterar o livro
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
    	this.quantidade = quantidade;
    	this.precoTotal = calcularPrecoTotal();
    }
    
    public double getPrecoTotal() {
    	return precoTotal;
    }
	
}
