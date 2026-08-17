package se.lexicon.data;

import se.lexicon.model.Book;
import se.lexicon.exception.*;

import java.util.List;

public interface BookDAO {

    List<Book> findAll() throws BookStorageException;

    void save(Book book) throws DuplicateBookException;

    Book findByTitle(String name) throws BookAvailableException;

}
