package se.lexicon.data;

import se.lexicon.model.Contact;
import se.lexicon.exception.ContactStorageException;
import se.lexicon.exception.DuplicateContactException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class FileContactDAOImpl implements ContactDAO {



    // add a liat because its simpler to read an write to the file at the start and end of the program

    @Override
    public void save(Contact contact) {

        Path path = Path.of("dir/contacts.txt");

        try ( BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND) ) {

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
    public List<Contact> findAll() {

        Path path = Paths.get("dir/contacts.txt");

        try ( Stream<String> lines = Files.lines(path) ) {
            // here we need to parse line by line and add them as a list of contacts
            lines.forEach( line -> {
                IO.println(line);
            } );


        } catch (IOException e) {
            throw new ContactStorageException ("Failed to read file!", e);
        }

        return null;
    }

    @Override
    public Contact findByName(String name) {
        // search from the contacts.txt directly


        List<Contact> cantacts = new ArrayList<>();
        Path path = Paths.get("dir/comtacts.txt");
        try ( Stream<String> lines = Files.lines(path) ) {

            lines.forEach( line -> {

                String[] contact = line.split(",");


            });


        } catch (IOException e) {
           throw new ContactStorageException ("Failed to read file!", e);
        }

        return contacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null); //   orelse throw error
    }

}
