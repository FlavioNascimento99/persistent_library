package Entities;

import java.util.*;

public class Cliente {
	private String cpf;
	private String nome;
	private List<Venda> listaDeVendas;
	
	Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
		this.listaDeVendas = new ArrayList<>(); // Inicializamos lista vazia de itens
	}
	
	public void adicionarVenda(Venda venda) {	
		this.listaDeVendas.add(venda);  		// Adiciona a venda à lista
	}
	
    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Venda> getListaDeVendas() {
        return listaDeVendas;
    }

    public void setListaDeVendas(List<Venda> listaDeVendas) {
        this.listaDeVendas = listaDeVendas;
    }
    
}
