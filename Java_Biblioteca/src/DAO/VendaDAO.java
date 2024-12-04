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

	public List<Venda> listarTodas() {
		Query query = database.query();	// Inicia uma query (consulta)
		query.constrain(Venda.class);	// Definido tipo de objeto desejado na query
		return query.execute();			// Executa a query
	}
	
	public Venda buscarPorId(int id) {	
		Query query = database.query();				  		  // Inicia consulta
		query.constrain(Venda.class);				  		  // Define tipo de objeto desejado na query
		query.descend("id").constrain(id);					  // Filtragem do objeto a partir do campo "id"
		List<Venda> resultado = query.execute();			  // Executa a consulta
		return resultado.isEmpty() ? null : resultado.get(0); // Retorna venda ou nulo ao fim do método.
	}

	public List<Venda> listaCliente(Cliente cliente) {
		Query query = database.query();
		query.constrain(Venda.class);
		query.descend("cliente").constrain(cliente);
		return query.execute();
	}

	public void deletar(Venda venda) {
        database.delete(venda); // Remove a venda do banco de dados
    }
}