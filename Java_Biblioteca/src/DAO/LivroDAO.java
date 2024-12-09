package DAO;

import java.util.List;

import com.db4o.*;
import com.db4o.query.*;

import Entities.Livro;

public class LivroDAO {
    private ObjectContainer database;

    // Construtor do DAO - Recebe a conexão com o banco de dados (ObjectContainer)
    public LivroDAO(ObjectContainer database) {
    	// Injeta o Banco de Dados (ObjectContainer) no DAO de Livro.
        this.database = database;          
    }

    // Método para salvar um objeto Livro no banco de dados
    public void salvar(Livro livro) {
        database.store(livro);             // Armazena o objeto Livro no banco de dados.
    }

    // Método para listar todos os livros armazenados no banco
    public List<Livro> listarTodos() {
        Query query = database.query();    // Inicia uma consulta no db4o por meio do seu método query().
        query.constrain(Livro.class);      // Restringe a consulta para buscar apenas objetos do tipo Livro.
        return query.execute();            // Executa a consulta e retorna a lista de objetos Livro encontrados.
    }

    // Método para buscar livros pelo título
    public List<Livro> buscaPorTitulo(String titulo) {
        Query query = database.query();    // Inicia uma nova consulta.
        query.constrain(Livro.class);      // Define que queremos buscar objetos do tipo Livro.
        query.descend("titulo")            // Acessa o atributo 'titulo' de cada objeto Livro.
             .constrain(titulo);           // Filtra a consulta para encontrar livros com o título correspondente ao parâmetro passado.
        return query.execute();            // Executa a consulta e retorna os livros encontrados que atendem ao critério de título.
    }

//    public List<Livro> livrosPromocao(Livro livro) {
// 	
//    	Double valorPromocional = 20.00;
//    	Query query = database.query();
//    	List<Livro> listagem = query.constrain(Livro.class);
//    	query.descend("preco").constrain();
//    	List<Livro> listagemPromocional = query.execute();
//    	if 
//    	return listagemPromocional.isEmpty() ? null : listagemPromocional;
//    	
//    }
}
