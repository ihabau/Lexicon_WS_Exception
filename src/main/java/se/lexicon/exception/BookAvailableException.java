package se.lexicon.exception;

/**
 * BookAvailableException — thrown when a book is expected to be available
 * (e.g. when lending it out) but is already borrowed.
 *
 * Unchecked for now; Step 4 turns it into part of a checked exception family.
 */
public class BookAvailableException extends RuntimeException {
    public BookAvailableException(String message) {
        super(message);
    }
}