package Entities;

import java.util.*;

public class Venda {
	private int id;
	private Cliente cliente;
	private double valorTotal;
	private List<ItemVenda> itens;
	
	public Venda(List<ItemVenda> itens, Cliente cliente) {
		this.cliente = cliente;
		this.itens = new ArrayList<>();
	}
	
	public void adicionarItem(ItemVenda item) {
		this.itens.add(item);
		calcularValorTotal(); 
	}
	
	// Getters
    public int getId() {
        return id;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public List<ItemVenda> getItens() {
        return itens;
    }
    public double getValorTotal() {
        return valorTotal;
    }

    // Método responsável por mapeamento de valores de ItemVenda (utilizando metodo acessor)
    public void calcularValorTotal() {
    	this.valorTotal = itens.stream()
    			.mapToDouble(item -> item.calcularPrecoTotal())
    			.sum();					
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
    
}
