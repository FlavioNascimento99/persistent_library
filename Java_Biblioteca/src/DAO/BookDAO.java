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


    // Construtor da Classe
    public BookDAO(EntityManager manager) {
    	this.manager = manager;
    }


        


    /**
     * Método de salvamento de livros.
     * 
    */
    public void save(Book book, EntityManager manager) {
        manager.persist(book);
    }


    /**
     * Método de alteração de dados do Banco.
     * 
     * Condicional: Joga exceção para caso o id passado a partir do parâmetro.
    */
    public void update(Book book, EntityManager manager) {
        if (book == null) {
            throw new IllegalArgumentException("O ID do livro nao pode ser nulo.");
        }
        manager.merge(book);
    }   
 

    /**
     * Método de exclusão de dados dentro do Banco.
     * 
    */
    public void delete(Book book, EntityManager manager) {
        manager.remove(book);
    }


    /**
     * Método de listagem de Livros cadastrados em bando a partir de uma consulta JPQL
     * 
     */
    public List<Book> search(String title) {
    	TypedQuery<Book> bookQuery = manager.createQuery("SELECT b FROM Book b WHERE LOWER (b.title) LIKE :title", Book.class);
        bookQuery.setParameter("title", "%" + title.toLowerCase() + "%");
        return bookQuery.getResultList();
    }


    /**
     * Método de listagem de Livros baseado em quantidade vendida
     * 
     * Obs: do maior(ou mais vendido) para o menos.
     */
    public List<Book> bestSellers() {
        String jpql = "SELECT b, SUM(itemSale.quantity) AS totalQuantity " +
                "FROM Book b JOIN ItemSale itemSale ON b.id = itemSale.book.id " +
                "GROUP BY b.id ORDER BY totalQuantity DESC";

        TypedQuery<Object[]> query = manager.createQuery(jpql, Object[].class);
        List<Object[]> results = query.getResultList();

        List<Book> bestSellers = new ArrayList<>();


        /**
         * Cria-se um array de objetos (<Book> e <Long>)
         * 
         */
        for (Object[] result : results) {
            Book book = (Book) result[0];
            Long totalQuantity = (Long) result[1];
            book.setQuantitySold(totalQuantity);
            bestSellers.add(book);
        }

        return bestSellers;
    }
}
