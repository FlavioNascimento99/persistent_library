package Entities;

import java.util.*;

public class Cliente {
    // Atributo identificador;
	private String cpf;
	private String nome;
    // Lista de itens do tipo Venda, responsável pela listagem de vendas feitas pelo Cliente;
	private List<Venda> listaDeVendas;
	
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
        // Inicializamos lista VAZIA de itens;
		this.listaDeVendas = new ArrayList<>(); 
	}
	
	public Cliente() {
		
	}
	
	public void adicionarVenda(Venda venda) {	
        // Adiciona a venda à lista;
		this.listaDeVendas.add(venda);
	}
	
    // Getters e Setters
    public String getCpf() {
        return cpf;
    }


    // Não vista a necessidade de fazer alteração de CPF a princípio, setter desabilitado.
    // public void setCpf(String cpf) {
    //     this.cpf = cpf;
    // }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Venda> getListaDeVendas() {
        return listaDeVendas;
    }

    // Não vista a necessidade de alterar listagem de vendas a princípio, setter desabilitado.
    // public void setListaDeVendas(List<Venda> listaDeVendas) {
    //     this.listaDeVendas = listaDeVendas;
    // }
    
}
