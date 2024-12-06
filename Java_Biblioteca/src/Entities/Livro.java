package Entities;

public class Livro {
	private int id;
	private String titulo;
	private String autor;
	private Double preco;
	
	public Livro(String titulo, String autor, Double preco){
		this.titulo = titulo;
		this.autor = autor;
		this.preco = preco;
	}
	
// Getters
    public int getId() {
        return id;
    }
    public String getAutor() {
        return autor;
    }
    public double getPreco() {
        return preco;
    }
    public String getTitulo() {
        return titulo;
    }

// Setters
    // public void setId(int id) {
    //     this.id = id;
    // }

    // Alteração de atributo
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    // Alteração de atributo
    public void setAutor(String autor) {
        this.autor = autor;
    }
    // Alteração de atributo - condicional que evita atribuição negativa.
    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.preco = preco;
    }
}
