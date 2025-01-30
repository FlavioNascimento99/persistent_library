package DAO;

import java.util.List;

import com.db4o.*;
import com.db4o.query.*;

import Entities.Client;
import Entities.Sale;

public class SaleDAO {
	private ObjectContainer database;
	
	public SaleDAO(ObjectContainer database) {
		this.database = database;
	}
	
	public void save(Sale sale) {
		database.store(sale);
		database.commit();
	}

	public List<Sale> listAll() {
		Query query = database.query();
		query.constrain(Sale.class);
		return query.execute();
	}
	
	public Sale searchById(int id) {	
		Query query = database.query();
		query.constrain(Sale.class);
		query.descend("id").constrain(id);
		List<Sale> result = query.execute();
		return result.isEmpty() ? null : result.get(0);
	}

	public List<Sale> listClient(Client client) {
		Query query = database.query();
		query.constrain(Sale.class);
		query.descend("cliente").constrain(client);
		return query.execute();
	}

	public void delete(Sale sale) {
        database.delete(sale);
    }
}