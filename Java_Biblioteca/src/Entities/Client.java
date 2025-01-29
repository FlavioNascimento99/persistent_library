package Entities;

import java.util.*;

public class Client {
	private String name;
	private String cpf;
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
