package Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books20192370034")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String title;
	private String author;
    private String isbn;
    private Long quantitySold;
    private double price;

	public Book(){}
	public Book(String title, String author, Double price, String isbn){
		this.title = title;
		this.author = author;
		this.price = price;
        this.isbn = isbn;
	}
	
	
	
    public int getId() {
        return id;
    }
    public String getAuthor() {
        return author;
    }
    public double getPrice() {
        return price;
    }
    public String getTitle() {
        return title;
    }
    public String getIsbn(){return isbn;}
    public Long getQuantitySold() {
        return quantitySold;
    }

    
    
    
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.price = price;
    }
    public void setQuantitySold(Long quantitySold) {
        this.quantitySold = quantitySold;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public static String formatISBN(String isbn) {
        return String.format("[ISBN-13]: %s-%s-%s-%s-%s",
                isbn.substring(0, 3),
                isbn.substring(3, 5),
                isbn.substring(5, 8),
                isbn.substring(8, 12),
                isbn.substring(12)
        );
    }
    @Override
    public String toString() {
    	return "Nome:  " + title + "\n" +
    		   "Autor: " + author +  "\n" +
               "ISBN: " + formatISBN(getIsbn()) + "\n" +
    		   "Preço: " + price +  "\n" +
    		   "Und. Vendidas: " + quantitySold + "\n";
    }

}
