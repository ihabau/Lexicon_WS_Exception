package se.lexicon.data;

import se.lexicon.exception.BookAvailableException;
import se.lexicon.exception.BookStorageException;
import se.lexicon.exception.DuplicateBookException;
import se.lexicon.model.Book;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * File-based implementation of {@link BookDAO} (Step 1 scaffolding — full DAO
 * handling is covered in Step 3 of the series).
 *
 * Responsibilities:
 * - keep an in-memory copy of the library (lazy-loaded from {@code dir/library.txt})
 * - persist new books by appending to the file
 * - protect against duplicate ISBNs / titles
 *
 * NOTE: exceptions here extend RuntimeException for now; Step 4 turns them into a
 * proper checked hierarchy handled by a central ExceptionHandler.
 */
public class BookDAOImpl implements BookDAO {

    private final List<Book> library = new ArrayList<>();
    private static final Path STORAGE = Paths.get("dir/library.txt");

    @Override
    public List<Book> findAll() throws BookStorageException {
        loadIfEmpty(); // first call reads the file into the in-memory list
        return library;
    }

    @Override
    public void save(Book book) throws DuplicateBookException {
        loadIfEmpty();

        // Reject duplicates BEFORE writing anything to disk.
        for (Book existing : library) {
            if (existing.getIsbn().equals(book.getIsbn())) {
                throw new DuplicateBookException("Book ISBN duplicate found.");
            }
            if (existing.getTitle().equals(book.getTitle())) {
                throw new DuplicateBookException("Book title duplicate found.");
            }
        }

        // Append one book per line: title,author,isbn,available
        try (BufferedWriter writer = Files.newBufferedWriter(STORAGE,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(book.getTitle());
            writer.write(",");
            writer.write(book.getAuthor());
            writer.write(",");
            writer.write(book.getIsbn());
            writer.write(",");
            writer.write(String.valueOf(book.isAvailable()));
            writer.newLine(); // without this every book lands on the same line
        } catch (IOException e) {
            throw new BookStorageException("Saving book to file failed.", e);
        }

        library.add(book); // keep the in-memory list in sync with the file
    }

    @Override
    public Book findByTitle(String name) throws BookAvailableException {
        loadIfEmpty();
        return library.stream()
                .filter(book -> book.getTitle().contentEquals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Lazy-loads the file into {@link #library} on the first call.
     * Creates {@code dir/library.txt} (including the parent directory) if missing.
     */
    private void loadIfEmpty() {
        if (!library.isEmpty()) {
            return; // already loaded
        }
        ensureFileExists();
        readFile();
    }

    // -- file helpers --

    private void ensureFileExists() {
        Path parent = STORAGE.getParent();
        try {
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(STORAGE)) {
                Files.createFile(STORAGE);
            }
        } catch (IOException e) {
            throw new BookStorageException("Could not create storage file.", e);
        }
    }

    private void readFile() {
        try (Stream<String> lines = Files.lines(STORAGE)) {
            lines.forEach(line -> {
                if (line.isBlank()) return; // skip empty lines
                String[] data = line.split(",");
                Book book = new Book(data[0], data[1], data[2], Boolean.parseBoolean(data[3]));
                library.add(book);
            });
        } catch (IOException e) {
            throw new BookStorageException("Reading library file failed.", e);
        }
    }
}