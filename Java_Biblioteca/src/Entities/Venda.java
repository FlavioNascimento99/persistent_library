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
		this.itens = new ArrayList<>();
		this.valorTotal = 0.0;
	}
	
	public void calcularValorTotal() {
		this.valorTotal = itens.stream()
							   .mapToDouble(item -> item.getPrecoTotal())
							   .sum();
							
	}
	
	public void adicionarItem(ItemVenda item) {
		this.itens.add(item);
		calcularValorTotal(); 
	}
	
	// Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
        calcularValorTotal();     // Recalcula o valor total ao alterar a lista
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
