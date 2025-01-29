package Entities;

public class ItemSale {
	private int id;
    private Book book;
	private int quantity;
    private double totalValue;

    // Construtor
	public ItemSale(Book book, int quantity) {
		this.book = book;
		this.quantity = quantity;
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
    	this.totalValue = calculateTotalValueBasedOnQuantity();
    }
    public double calculateTotalValueBasedOnQuantity() {
		return book.getPrice() * quantity;
	}
    
    
    
}
