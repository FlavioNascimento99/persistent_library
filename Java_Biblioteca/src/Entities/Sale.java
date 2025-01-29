package Entities;

import java.util.*;

public class Sale {
	private int id;
	private Client client;
	private Date dateSale;
	private double totalValue;
	private List<ItemSale> itemsSale;
	
	public Sale(List<ItemSale> itemsSale, Client client, Date dateSale) {
		this.client = client;
		this.dateSale = dateSale;
		this.itemsSale = itemsSale != null ? new ArrayList<>(itemsSale) : new ArrayList<>();
		
	}
	
	// Adiciona itens à uma lista de Itens
	public void addItem(ItemSale item) {
		this.itemsSale.add(item);
		calculateTotalSaleValue(); 
	}
	
	
	
	// Getters 
    public int getId() {
        return id;
    }
    
	public Date getDateSale() {
		return dateSale;
	}
    
    public Client getClient() {
        return client;
    }
    
    public List<ItemSale> getItems() {
        return itemsSale;
    }

    
    
    // Setters
    public void setClient(Client client) {
        this.client = client;
    }

    public void setId(int id) {
        this.id = id;
    }

	public void setDateSale(Date dateSale) {
		this.dateSale = dateSale;
	}
	
    public void setItens(List<ItemSale> items) {
        this.itemsSale = items;
        calculateTotalSaleValue(); 
    }
    
    
    public double calculateTotalSaleValue() {
    	return totalValue = itemsSale.stream()
    			.mapToDouble(item -> item.calculateTotalValueBasedOnQuantity())
    			.sum();					
    }



    

    
}
