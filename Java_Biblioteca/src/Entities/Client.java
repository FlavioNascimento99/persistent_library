package Entities;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "clients")
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String cpf;
	private String name;
	
	// One single Client has MANY Sale's into a list of it.
	@OneToMany(mappedBy="client")
	private List<Sale> salesList;
	
	public Client(String name, String cpf) {
		this.name = name;
		this.cpf = cpf;
		this.salesList = new ArrayList<>(); 
	}
	
	public Client() {
		
	}
	
	public void addSale(Sale sale) {	
		this.salesList.add(sale);
	}
	
    public String getCpf() {
        return cpf;
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
    
    @Override
    public String toString() {
        String salesString = (salesList == null || salesList.isEmpty()) ? "Nenhuma venda registrada." : salesList.toString();
        return String.format("Nome: %s%n, CPF: %s%n Vendas: %s%n ", name, cpf, salesString);
    }
}
