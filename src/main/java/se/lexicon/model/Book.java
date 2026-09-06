package se.lexicon.model;

/**
 * The domain model for a library book — Step 1 of the workshop series.
 *
 * This class mirrors {@code se.lexicon.model} from the Event Management App:
 * plain Java with private fields, getters, a validating constructor and a
 * readable {@code toString()}.
 *
 * WORKSHEET RULES:
 * - title/author/isbn are READ-ONLY after creation (only {@code setAvailable} exists).
 * - title/author must not be blank, isbn must be exactly 13 digits
 *   -> IllegalArgumentException (unchecked) suits model validation.
 * Primary constructor used when a NEW book is added to the library.
 * A new book is always available by default.
 * Constructor used when loading an EXISTING book (e.g. from the file),
 * so the caller can restore the saved availability.
 * Validates all fields before assigning them.
 * Checks whether this book and {@code other} describe the same publication.
 * Two books are considered the same when their ISBNs match.
 * Rejects blank (empty / whitespace-only) titles and authors.
 * Rejects ISBNs that are not exactly 13 digits.
 * Bonus challenge helper — reusable static check for a valid ISBN.
 * Used later by the DAO layer to validate input before saving.
 */

public class Book {

    private String title;
    private String author;
    private String isbn;
    private boolean available;


    public Book(String title, String author, String isbn, boolean available) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
    }


    // getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    // setters

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


}
