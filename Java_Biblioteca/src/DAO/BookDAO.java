package DAO;

import java.util.List;

import com.db4o.*;
import com.db4o.query.*;

import Entities.Book;

public class BookDAO {
    private ObjectContainer database;
    public BookDAO(ObjectContainer database) {
        this.database = database;          
    }

    public void save(Book book) {
        database.store(book);
    }

    public List<Book> listAll() {
        Query query = database.query();
        query.constrain(Book.class);
        return query.execute();
    }

    public List<Book> searchByTitle(String title) {
        Query query = database.query();
        query.constrain(Book.class);
        query.descend("titulo")   
             .constrain(title);  
        return query.execute();
    }

    
//		TODO: Look this shit you motherfucker, dang....
    
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
