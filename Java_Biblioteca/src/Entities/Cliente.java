package Entities;

import java.util.*;

public class Cliente {
    // Atributo identificador;
	private String cpf;
	private String nome;
	private List<Venda> listaDeVendas;
	
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
		this.listaDeVendas = new ArrayList<>(); 
	}
	
	public Cliente() {
		
	}
	
	public void adicionarVenda(Venda venda) {	
		this.listaDeVendas.add(venda);
	}
	
    public String getCpf() {
        return cpf;
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
    
    @Override
    public String toString() {
        String vendasString = (listaDeVendas == null || listaDeVendas.isEmpty()) ? "Nenhuma venda registrada." : listaDeVendas.toString();
        return String.format("Nome: %s, CPF: %s%nVendas: %s%n", nome, cpf, vendasString);
    }
}
