package se.lexicon.data;

import se.lexicon.model.Book;
import se.lexicon.exception.BookStorageException;
import se.lexicon.exception.DuplicateBookException;
import se.lexicon.exception.BookAvailableException;

import java.util.List;

/**
 * The DAO contract (Step 1 scaffolding — full DAO handling is Step 3 in the series).
 *
 * An interface defines WHAT the app needs from storage without locking the
 * implementation to a specific technology (file, database, in-memory).
 * The controller/service depends on this interface, not on {@link BookDAOImpl},
 * so the storage backend can be swapped later (Step 7 → MySQL) without touching
 * the other layers.
 */
public interface BookDAO {

    /** Returns all books currently in the library. */
    List<Book> findAll() throws BookStorageException;

    /** Persists a new book; throws if a book with the same ISBN or title exists. */
    void save(Book book) throws DuplicateBookException;

    /** Finds the first book with the given exact title. */
    Book findByTitle(String name) throws BookAvailableException;

}