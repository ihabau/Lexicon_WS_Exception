package se.lexicon.exception;

import java.io.IOException;

public class ContactStorageException extends Exception {
    public ContactStorageException(String message, IOException e) {
        super(message, e);
    }
}
