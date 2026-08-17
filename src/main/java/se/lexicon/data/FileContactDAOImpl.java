package se.lexicon.data;

import se.lexicon.exception.ContactStorageException;
import se.lexicon.exception.DuplicateContactException;

import se.lexicon.model.Contact;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class FileContactDAOImpl implements ContactDAO {

    private List<Contact> contacts = new ArrayList<>();

    @Override
    public void save(Contact contact) throws ContactStorageException, DuplicateContactException {
        if (contacts.isEmpty()) {
            listPopulate();
        }

        Path path = Path.of("dir/contacts.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND)) {

            writer.append(contact.getName());
            writer.append(",");
            writer.append(contact.getPhoneNumber());
            writer.newLine();

        } catch (IOException e) {
            throw new ContactStorageException("Failed to write to file!", e);
        }

        contacts.add(contact);
    }

    @Override
    public List<Contact> findAll() throws ContactStorageException {
        if (contacts.isEmpty()) {
            listPopulate();
        }

        return contacts;
    }

    @Override
    public Contact findByName(String name) throws ContactStorageException {
        if (contacts.isEmpty()) {
            listPopulate();
        }

        return contacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    private void listPopulate() throws ContactStorageException {
        Path path = Paths.get("dir/contacts.txt");
        Path parentDir = path.getParent();

        try {
            if (!Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new ContactStorageException("Failed to create file structure!", e);
        }

        if (contacts.isEmpty()) {
            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(line -> {
                    String[] contactList = line.split(",");
                    Contact contact = new Contact(contactList[0], contactList[1]);
                    contacts.add(contact);
                });
            } catch (IOException e) {
                throw new ContactStorageException("Failed to read file!", e);
            }
        }
    }
}