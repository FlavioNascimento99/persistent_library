package Entities;



public class ItemVenda {
	private int id;
	// Atributo do tipo Livro;
    private Livro livro;
	private int quantidade;
    // Preço total do item, Livro * Quantidade;
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
    // public void setId(int id) {
    //     this.id = id;
    // }

    // Faz recalculo do preço total ao alterar o livro. 
    public void setLivro(Livro livro) {
        this.livro = livro;
        this.precoTotal = calcularPrecoTotal(); 
    }
    // Alterar quantidade de livros e recalcular o valor total. 
    public void setQuantidade(int quantidade) {
    	this.quantidade = quantidade;
    	this.precoTotal = calcularPrecoTotal();
    }
    // Utiliza getter de "Preco", multiplicado pela quantidade.
    public Double calcularPrecoTotal() {
		return livro.getPreco() * quantidade;
	}
    
}
