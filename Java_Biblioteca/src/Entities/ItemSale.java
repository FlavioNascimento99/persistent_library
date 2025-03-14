package Entities;

import javax.persistence.*;

@Entity
@Table(name = "items_sales20192370034")
public class ItemSale {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="sale_id", nullable=false)
	private Sale sale = new Sale();
	
	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double totalValue;



    public ItemSale(){}
	public ItemSale(Book book, int quantity) {
		this.book = book;
		this.quantity = quantity;
		this.totalValue = calculateTotalValue();
	}
	
	

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

    

    public void setBook(Book book) {
        this.book = book;
    }
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    	reCalculateTotalValue();
    }
    public void setSale(Sale sale) {
        this.sale = sale;
    }



    public double calculateTotalValue() {
    	return (book != null) ? book.getPrice() * quantity : 0 ;
    }
    public void reCalculateTotalValue() {
		this.totalValue = calculateTotalValue();
	}
    
}
