package se.lexicon.exception;

/**
 * ExceptionHandler — placeholder for Step 4 of the series.
 *
 * In the final architecture this class is NOT an exception itself; it is the
 * single place where any caught exception is turned into a friendly, user-readable
 * message via the view. Here it just marks the concept until Step 4 formalizes it.
 */
public class ExceptionHandler extends RuntimeException {
    public ExceptionHandler(String message) {
        super(message);
    }
}