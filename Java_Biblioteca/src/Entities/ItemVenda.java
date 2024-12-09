package Entities;

public class ItemVenda {
	private int id;
    private Livro livro;
	private int quantidade;
    private double precoTotal;

	public ItemVenda(Livro livro, int quantidade) {
		this.livro = livro;
		this.quantidade = quantidade;
        // Não é necessário construir "precoTotal", já que o mesmo é um atributo composto por um calculo.
	}
	
	// Getters
    public int getId() {
        return id;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public Livro getLivro() {
        return livro;
    }
    public double getPrecoTotal() {
    	return precoTotal;
    }

    // Setters
    public void setLivro(Livro livro) {
        this.livro = livro;
        this.precoTotal = calcularPrecoTotal(); 
    }
    public void setQuantidade(int quantidade) {
    	this.quantidade = quantidade;
    	this.precoTotal = calcularPrecoTotal();
    }
    public Double calcularPrecoTotal() {
		return livro.getPreco() * quantidade;
	}
    
}
