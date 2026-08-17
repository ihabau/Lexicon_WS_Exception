package se.lexicon.data;

import se.lexicon.exception.BookAvailableException;
import se.lexicon.exception.BookStorageException;
import se.lexicon.exception.DuplicateBookException;
import se.lexicon.model.Book;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BookDAOImpl implements BookDAO {

    private final List<Book> library = new ArrayList<>();

    @Override
    public List<Book> findAll() throws BookStorageException {

        if (library.isEmpty()){
            try {
                bufferLibraryData();
            } catch (BookStorageException e) {
                throw new BookStorageException( "Loading from file failed." );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return library;
    }

    @Override
    public void save(Book book) throws DuplicateBookException {

        if (library.isEmpty()){
            try {
                bufferLibraryData();
            } catch (BookStorageException e) {
                throw new BookStorageException( "Loading from file failed." );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        library.forEach(book1 -> {
            if ( book1.getIsbn().equals(book.getIsbn())) {
                throw new DuplicateBookException("Book ISBN duplicate found.");
            }
            if ( book1.getTitle().equals(book.getTitle())) {
                throw new DuplicateBookException("Book title duplicate found.");
            }
        });

        Path path = Path.of("dir/library.txt");

        try(BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.append(book.getTitle());
            writer.append(",");
            writer.append(book.getAuthor());
            writer.append(",");
            writer.append(book.getIsbn());
            writer.append(",");
            writer.append( String.valueOf(book.isAvailable()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Book findByTitle(String name) throws BookAvailableException {
        return library.stream()
                .filter(book -> book.getTitle().contentEquals(name))
                .findFirst().orElse(null);
    }

    private void bufferLibraryData () throws BookStorageException, IOException {

        Path path = Paths.get("dir/library.txt");
        Path pathParent = path.getParent();

        if ( !Files.exists(pathParent)) {
            try {
                Files.createDirectories(pathParent);
            } catch (BookStorageException e) {
                throw new BookStorageException("Creating directory failed.");
            }
        }
        if ( !Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (BookStorageException e) {
                throw new BookStorageException("Creating file failed.");
            }
        }

        if (library.isEmpty()) {
            try ( Stream<String> lines = Files.lines(path)) {

                lines.forEach( line -> {
                    String[] bookData = line.split(",");
                    Book book = new Book( bookData[0], bookData[1], bookData[2], Boolean.parseBoolean(bookData[3]) );
                    library.add(book);
                });

            } catch (IOException e) {
                throw new IOException("Failed to read file.", e);
            }
        }

    }
}
