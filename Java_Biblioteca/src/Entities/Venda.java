package Entities;

import java.util.*;

public class Venda {
	private int id;
	private Cliente cliente;
	private Date data;
	private double valorTotal;
	private List<ItemVenda> itens;
	
	Venda(int id, List<ItemVenda> itens, Cliente cliente, Date data, double valorTotal) {
		this.id = id;
		this.cliente = cliente;
		this.data = data;
        this.valorTotal = 0.0;

		this.itens = new ArrayList<>();
	}
	
    // Método responsável por mapeamento de valores de ItemVenda (utilizando metodo acessor)
	public void calcularValorTotal() {
		this.valorTotal = itens.stream()
							   .mapToDouble(item -> item.getPrecoTotal())
							   .sum();					
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
    public Date getData() {
        return data;
    }
    public double getValorTotal() {
        return valorTotal;
    }


    // Setters
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
        
        // Recalcula o valor total ao alterar a lista
        calcularValorTotal(); 
    }
    
}
