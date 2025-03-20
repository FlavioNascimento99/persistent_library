package DAO;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


import Entities.Book;
import Interfaces.BookDAO;
import Utils.Database;

public class BookDAOImpl extends GenericDAOImpl<Book, Integer> implements BookDAO {
	
    public BookDAOImpl(EntityManager manager) {
  
        super(manager, Book.class);
    }

    public Book searchByTitle(String title) {
        TypedQuery<Book> query = manager.createQuery("SELECT b FROM Book b WHERE LOWER (b.title) = :title", Book.class);
        query.setParameter("title", title);
        return query.getSingleResult();
    }

    public List<Book> listBookByStringTitle(String title) {
    	TypedQuery<Book> bookQuery = manager.createQuery("SELECT b FROM Book b WHERE LOWER (b.title) LIKE :title", Book.class);
        bookQuery.setParameter("title", "%" + title.toLowerCase() + "%");
        return bookQuery.getResultList();
    }
    public List<Book> listTopSellingBooks() {
        String jpql = "SELECT i.book, SUM(i.quantity) FROM ItemSale i GROUP BY i.book ORDER BY SUM(i.quantity) DESC";
        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        List<Object[]> results = query.getResultList();

        List<Book> bestSellers = new ArrayList<>();
        for (Object[] result : results) {
            Book book = (Book) result[0];
            Long quantitySold = ((Number) result[1]).longValue();
            book.setQuantitySold(quantitySold);
            bestSellers.add(book);
        }

        return bestSellers;
    }
}
