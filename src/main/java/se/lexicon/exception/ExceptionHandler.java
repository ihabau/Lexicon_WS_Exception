package se.lexicon.exception;

public class ExceptionHandler {

    public static void handle(Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
