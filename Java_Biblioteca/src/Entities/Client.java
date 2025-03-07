package Entities;

import java.util.*;

import javax.persistence.*;


@Entity
@Table(name = "clients")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy="client")
	private List<Sale> salesList;

	public Client(String name) {
		this.name = name;
		this.salesList = new ArrayList<>();
	}
	
	public Client() {
		
	}
	

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sale> getSalesList() {
        return salesList;
    }

    public void addSale(Sale sale) {
        this.salesList.add(sale);
    }

    
    @Override
    public String toString() {
        return String.format("Nome: %s%n \n" +
                "CPF: %s%n \n" +
                "Vendas: %s%n ", name, id, getSalesList());
    }
}
