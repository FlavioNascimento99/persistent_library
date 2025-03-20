package Entities;

import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.*;


@Entity
@Table(name = "sales20192370034")
public class Sale {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	@Column(nullable = false)
	private LocalDate dateSale;

	@Column(nullable = false)
	private double totalValue;
	
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemSale> itemsSale = new ArrayList<>();




	public Sale(){}
	public Sale(List<ItemSale> itemsSale, Client client, LocalDate dateSale) {
		this.client = client;
		this.dateSale = dateSale;
		this.itemsSale = itemsSale != null ? new ArrayList<>(itemsSale) : new ArrayList<>();
	}




    public Long getId() {
		return id;
    }
    public void getItems() {
		for (ItemSale items : itemsSale) {
			System.out.println(items);
		}
    }
    public Client getClient() {
		return client;
    }
	public double getTotalValue() {
		return totalValue;
	}
	public LocalDate getDateSale() {
		return dateSale;
	}




	public void setId(Long id) {
		this.id = id;
    }
	public void setItens(List<ItemSale> items) {
        this.itemsSale = items;
        calculateTotalSaleValue();
	}
    public void setClient(Client client) {
		this.client = client;
	}
	public void setDateSale(LocalDate dateSale) {
		this.dateSale = dateSale;
	}



	public void addItem(ItemSale item) {
		this.itemsSale.add(item);
		calculateTotalSaleValue();
	}
    public double calculateTotalSaleValue() {
    	return totalValue = itemsSale.stream()
    			.mapToDouble(item -> item.calculateTotalValue())
    			.sum();
    }



	@Override
	public String toString() {
		return String.format("ID: %s%n" +
				"Nome do Cliente: %s%n" +
				"Data da Venda: %s%n" +
				"Valor total da Venda: %s%n ", id, getClient().getName(), dateSale, totalValue);
	}

}
