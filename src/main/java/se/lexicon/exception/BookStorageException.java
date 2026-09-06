package se.lexicon.exception;

/**
 * BookStorageException — wraps low-level I/O failures (file missing, unreadable,
 * write error) so the rest of the app never has to deal with raw IOException.
 *
 * Unchecked for now; Step 4 makes it a checked exception in the family.
 */
public class BookStorageException extends RuntimeException {
    public BookStorageException(String message) {
        super(message);
    }

    public BookStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}