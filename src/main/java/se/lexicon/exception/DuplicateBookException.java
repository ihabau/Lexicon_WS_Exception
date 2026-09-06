package se.lexicon.exception;

/**
 * DuplicateBookException — thrown when a book with the same ISBN or title
 * already exists in the library.
 *
 * Extends RuntimeException (unchecked) for now. In Step 4 of the series the
 * exception family is rebuilt as a checked hierarchy under a common base class
 * and every error is routed through a central ExceptionHandler.
 */
public class DuplicateBookException extends RuntimeException {
    public DuplicateBookException(String message) {
        super(message);
    }
}