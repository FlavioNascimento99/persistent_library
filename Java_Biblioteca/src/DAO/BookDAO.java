package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


import Entities.Book;
import org.apache.log4j.Logger;

public class BookDAO extends GenericDAO<Book, Integer> {

    public BookDAO(EntityManager manager) {
        super(manager, Book.class);
    }

    public List<Book> search(String title) {
    	TypedQuery<Book> bookQuery = manager.createQuery("SELECT b FROM Book b WHERE LOWER (b.title) LIKE :title", Book.class);
        bookQuery.setParameter("title", "%" + title.toLowerCase() + "%");
        return bookQuery.getResultList();
    }

    public List<Book> bestSellers() {
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
