package Entities;

import java.util.*;

import javax.persistence.*;


@Entity
@Table(name = "sales")
public class Sale {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	@Column(nullable = false)
	private Date dateSale;

	@Column(nullable = false)
	private double totalValue;
	
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemSale> itemsSale = new ArrayList<>();

	// Constructors
	public Sale(){}
	
	public Sale(List<ItemSale> itemsSale, Client client, Date dateSale) {
		this.client = client;
		this.dateSale = dateSale;
		this.itemsSale = itemsSale != null ? new ArrayList<>(itemsSale) : new ArrayList<>();
	}
	
	// Getters 
    public Long getId() {

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
    public void setId(Long id) {

		this.id = id;
    }
	public void setDateSale(Date dateSale) {

		this.dateSale = dateSale;
	}
    public void setItens(List<ItemSale> items) {
        this.itemsSale = items;
        calculateTotalSaleValue(); 
    }


	// Utils
    public double calculateTotalSaleValue() {
    	return totalValue = itemsSale.stream()
    			.mapToDouble(item -> item.calculateTotalValue())
    			.sum();
    }
	public void addItem(ItemSale item) {
		this.itemsSale.add(item);
		calculateTotalSaleValue();
	}
}
