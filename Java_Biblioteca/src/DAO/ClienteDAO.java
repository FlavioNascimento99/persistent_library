package DAO;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import Entities.Cliente;

public class ClienteDAO {
    private ObjectContainer database;

    public ClienteDAO(ObjectContainer database) {
        this.database = database;
    }

    public void salvar(Cliente cliente) {
        database.store(cliente);
        database.commit();
    }

    public Cliente buscaPorCpf(String cpf) {
        // Criando a consulta
        Query query = database.query();
        query.constrain(Cliente.class);        // Filtra objetos do tipo Cliente.
        query.descend("cpf").constrain(cpf);   // Filtra pelo atributo "cpf".
        
        // Executa a consulta e retorna o primeiro cliente encontrado
        List<Cliente> resultado = query.execute();
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public List<Cliente> listarTodos() {
        // Criando a consulta para listar todos os clientes
        Query query = database.query();
        query.constrain(Cliente.class);
        return query.execute();
    }

    public void deletar(Cliente clienteADeletar) {
        Query query = database.query();
        query.constrain(Cliente.class);
        query.descend("cpf").constrain(clienteADeletar);
        
        database.delete(clienteADeletar);
        database.commit();
    }
}
