package Entities;

import java.time.LocalDate;
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
	private LocalDate dateSale;

	@Column(nullable = false)
	private double totalValue;
	
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemSale> itemsSale = new ArrayList<>();





	//******************************//
	//								//
	//			Constructor			//
	//								//
	//******************************//
	public Sale(){}

	public Sale(List<ItemSale> itemsSale, Client client, LocalDate dateSale) {
		this.client = client;
		this.dateSale = dateSale;
		this.itemsSale = itemsSale != null ? new ArrayList<>(itemsSale) : new ArrayList<>();
	}





	//******************************//
	//								//
	//			Getters				//
	//								//
	//******************************//
    public Long getId() {

		return id;

    }

	public LocalDate getDateSale() {

		return dateSale;

	}

    public Client getClient() {

		return client;

    }

    public void getItems() {
		for (ItemSale items : itemsSale) {
			System.out.println(items);
		}
    }

	public double getTotalValue() {

		return totalValue;

	}




	//******************************//
	//								//
	//			Setters				//
	//								//
	//******************************//
    public void setClient(Client client) {

		this.client = client;

	}

	public void setId(Long id) {

		this.id = id;

    }

	public void setDateSale(LocalDate dateSale) {

		this.dateSale = dateSale;

	}

	public void setItens(List<ItemSale> items) {

        this.itemsSale = items;

        calculateTotalSaleValue();

	}





	//******************************//
	//								//
	//			Utilitários			//
	//								//
	//******************************//
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
				"Data da Venda: %s%n" +
				"Valor total da Venda: %s%n ", id, dateSale, totalValue);
	}

}
