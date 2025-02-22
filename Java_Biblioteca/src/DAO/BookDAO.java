package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Book;

public class BookDAO {
	private EntityManager manager;
  	
	// Bad constructor... 
    public BookDAO(){}
    
    public BookDAO(EntityManager manager) {
    	this.manager = manager;
    }

    public void save(Book book,EntityManager manager) {
        manager.persist(book);
        manager.flush();
    }

    public List<Book> listAll() {
    	TypedQuery<Book> bookQuery = manager.createQuery("select b from Book b", Book.class);
    	List<Book> resultQuery = bookQuery.getResultList();
    	return resultQuery;
    }
    
    // List maybe doesn't make sense at this one, but is a minimal issue fr
    public List<Book> searchByTitle(String title) {
    	TypedQuery<Book> bookQuery = manager.createQuery("select b from Book b where b.title = :title", Book.class);
    	bookQuery.setParameter("title", title);
    	return bookQuery.getResultList();
    }
  

}
