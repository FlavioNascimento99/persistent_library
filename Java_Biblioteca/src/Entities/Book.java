package Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books20192370034")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private String author;
    private String year;
    private String genre;
    private String isbn;
    private Long quantitySold;
    private Double price;


	public Book(){}
	public Book(String isbn, String title, String author, String year, String genre, Double price){
		this.title = title;
		this.author = author;
		this.price = price;
        this.isbn = isbn;
        this.year = year;
        this.genre = genre;
	}
	
	
	
    public int getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }
    public String getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }
    public String getIsbn(){return isbn;}
    public String getYear() {
        return year;
    }
    public String getGenre() {
        return genre;
    }
    public Long getQuantitySold() {
        return quantitySold;
    }



    public void setYear(String year) {
        this.year = year;
    }
    public void setGenre(String genre) {
        this.genre = genre;
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
