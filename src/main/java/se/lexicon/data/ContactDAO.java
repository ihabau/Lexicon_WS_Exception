package se.lexicon.data;

import se.lexicon.model.Contact;
import se.lexicon.exception.ContactStorageException;
import se.lexicon.exception.DuplicateContactException;

import java.util.List;

public interface ContactDAO {

    List<Contact> findAll() throws ContactStorageException;

    void save(Contact contact) throws ContactStorageException, DuplicateContactException;

    Contact findByName(String name) throws ContactStorageException;

}
