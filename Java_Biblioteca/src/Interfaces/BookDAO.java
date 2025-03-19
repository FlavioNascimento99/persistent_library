package Interfaces;

import Entities.Book;
import java.util.List;

public interface BookDAO extends GenericDAO<Book> {

    Book searchByTitle(String title);
    List<Book> listBookByStringTitle(String stringTitle);
    List<Book> listTopSellingBooks();
}
