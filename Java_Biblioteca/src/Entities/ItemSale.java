package Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "items_sales")
public class ItemSale {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    private int quantity;
    private double totalValue;

    // Constructors
    public ItemSale(){}
    
	public ItemSale(Book book, int quantity) {
		this.book = book;
		this.quantity = quantity;
		this.totalValue = calculateTotalValue();
	}
	
	
	
	// Getters
    public int getId() {
        return id;
    }
    public int getQuantity() {
        return quantity;
    }
    public Book getBook() {
        return book;
    }
    public double getTotalValue() {
    	return totalValue;
    }

    
    
    // Setters
    public void setBook(Book book) {
        this.book = book;
    }
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    	reCalculateTotalValue();
    }
    
    public double calculateTotalValue() {
    	return (book != null) ? book.getPrice() * quantity : 0 ;
    }
    
    public void reCalculateTotalValue() {
		this.totalValue = calculateTotalValue();
	}
    
    
    
}
