package Entities;

import java.util.*;

public class Venda {
	private int id;
	private Cliente cliente;
	private double valorTotal;
	private List<ItemVenda> itens;
	
	// Constructor
	public Venda(List<ItemVenda> itens, Cliente cliente) {
		this.cliente = cliente;
		this.itens = new ArrayList<>();
	}
	
	// Add Items
	public void adicionarItem(ItemVenda item) {
		this.itens.add(item);
		calcularValorTotal(); 
	}
	
	// Getters
    public int getId() {
        return id;
    }
    
    // Get Cliente
    public Cliente getCliente() {
        return cliente;
    }
    
    // Get Items<List>
    public List<ItemVenda> getItens() {
        return itens;
    }

    // Setters
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
        
        // Recalcula o valor total ao alterar a lista
        calcularValorTotal(); 
    }
    
    // Método responsável por mapeamento de valores de ItemVenda (utilizando metodo acessor)
    public double calcularValorTotal() {
    	return valorTotal = itens.stream()
    			.mapToDouble(item -> item.calcularPrecoTotal())
    			.sum();					
    }
    

    
}
