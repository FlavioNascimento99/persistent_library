package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Book;

public class BookDAO {
	private EntityManager manager;

    public BookDAO(EntityManager manager) {
    	this.manager = manager;
    }


    public void save(Book book, EntityManager manager) {

        manager.persist(book);

    }

    public void update(Book book, EntityManager manager) {
        if (book == null) {
            throw new IllegalArgumentException("O ID do livro nao pode ser nulo.");
        }
        manager.merge(book);
    }

    public void delete(Book book, EntityManager manager) {

        manager.remove(book);

    }

    public List<Book> list() {

    	TypedQuery<Book> bookQuery = manager.createQuery("SELECT b FROM Book b", Book.class);
    	List<Book> resultQuery = bookQuery.getResultList();
    	return resultQuery;

    }

    public List<Book> search(String title) {

    	TypedQuery<Book> bookQuery = manager.createQuery("SELECT b FROM Book b WHERE LOWER (b.title) LIKE :title", Book.class);
        bookQuery.setParameter("title", "%" + title.toLowerCase() + "%");
        return bookQuery.getResultList();

    }

}
