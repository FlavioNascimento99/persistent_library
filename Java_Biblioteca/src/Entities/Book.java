package Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String title;
	private String author;
	private double price;
    private Long quantitySold;
	
	// Constructors
	public Book(){}
	
	public Book(String title, String author, Double price){
		this.title = title;
		this.author = author;
		this.price = price;
	}
	
	
	
	// Getters
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
    
    @Override
    public String toString() {
    	return "Nome:  " + title + "\n" +
    		   "Autor: " + author +  "\n" + 
    		   "Preço: " + price +  "\n" +
    		   "Und. Vendidas: " + quantitySold + "\n";
    }

    public Long getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Long quantitySold) {
        this.quantitySold = quantitySold;
    }
}
