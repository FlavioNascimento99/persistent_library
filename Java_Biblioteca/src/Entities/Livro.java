package Entities;

public class Livro {
	private int id;
	private String titulo;
	private String autor;
	private Double preco;
	
	Livro(int id, String titulo, String autor, Double preco){
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.preco = preco;
	}
	
    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.preco = preco;
    }
}
