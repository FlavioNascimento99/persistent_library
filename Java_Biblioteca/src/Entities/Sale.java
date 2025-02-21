package Entities;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "sales")
public class Sale {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "client_cpf", nullable = false)
	private Client client;
	
	@Temporal(TemporalType.DATE)
	private Date dateSale;
	
	private double totalValue;
	
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
	private List<ItemSale> itemsSale;
	
	
	// Constructors
	public Sale(){}
	
	public Sale(List<ItemSale> itemsSale, Client client, Date dateSale) {
		this.client = client;
		this.dateSale = dateSale;
		this.itemsSale = itemsSale != null ? new ArrayList<>(itemsSale) : new ArrayList<>();
	}
	
	
	
	
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
    			.mapToDouble(item -> item.calculateTotalValue())
    			.sum();
    }



    

    
}
