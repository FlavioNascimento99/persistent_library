package Entities;

import java.util.*;

import javax.persistence.*;


@Entity
@Table(name = "clients20192370034")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false)
	private String name;

    @Column(nullable = false)
    private String cpf;
	
    @OneToMany(mappedBy="client")
	private List<Sale> salesList;



	public Client() {}
	public Client(String name, String cpf) {
		this.name = name;
        this.cpf = cpf;
		this.salesList = new ArrayList<>();
	}
	


    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCpf(){ return cpf; }
    public List<Sale> getSalesList() {
        return salesList;
    }



    public void setName(String name) {
        this.name = name;
    }
    public void setCpf(String cpf) {this.cpf = cpf;}


    public void addSale(Sale sale) {
        this.salesList.add(sale);
    }



    @Override
    public String toString() {
        return String.format("Nome: %s%n " +
                "CPF: %s%n " +
                "Vendas: %s%n ", name, cpf, getSalesList());
    }

}
