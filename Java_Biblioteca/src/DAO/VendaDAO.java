package DAO;

import com.db4o.*;

import Entities.Venda;

public class VendaDAO {
	private ObjectContainer database;
	
	public VendaDAO(ObjectContainer database) {
		this.database = database;
	}
	
	public void salvar(Venda venda) {
		database.store(venda);
	}
	
}
