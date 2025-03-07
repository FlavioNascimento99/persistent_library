package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Entities.Book;
import org.apache.log4j.Logger;

public class BookDAO {
    private static final Logger logger = Logger.getLogger(BookDAO.class);
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


    public long getQuantitySoldById(int bookId) {
        String jpql = "SELECT SUM(is.quantity) FROM ItemSale is WHERE is.book.id = :bookId";
        TypedQuery<Long> bookQuery = manager.createQuery(jpql, Long.class);
        bookQuery.setParameter("bookId", bookId);

        Long resultQuery = bookQuery.getSingleResult();
        return resultQuery != null ? resultQuery : 0;
    }

    public List<Book> bestSellers() {
        String jpql = "SELECT b, SUM(itemSale.quantity) AS totalQuantity " +
                "FROM Book b JOIN ItemSale itemSale ON b.id = itemSale.book.id " +
                "GROUP BY b.id ORDER BY totalQuantity DESC";

        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        List<Object[]> results = query.getResultList();

        List<Book> bestSellers = new ArrayList<>();

        for (Object[] result : results) {
            Book book = (Book) result[0];  // Book entity
            Long totalQuantity = (Long) result[1];  // Total quantity sold
            book.setQuantitySold(totalQuantity);  // Set the quantity sold in the book
            bestSellers.add(book);
        }

        return bestSellers;
    }
}
