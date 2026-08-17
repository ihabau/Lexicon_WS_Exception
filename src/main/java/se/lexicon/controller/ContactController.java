package se.lexicon.controller;

import se.lexicon.data.ContactDAO;
import se.lexicon.exception.ContactStorageException;
import se.lexicon.exception.DuplicateContactException;
import se.lexicon.exception.ExceptionHandler;
import se.lexicon.model.Contact;
import se.lexicon.view.ContactView;

import java.util.List;
import java.util.Scanner;

public class ContactController {

    private final ContactDAO contactDAO;
    private final ContactView contactView;

    public ContactController(ContactView contactView, ContactDAO contactDAO) {
        this.contactView = contactView;
        this.contactDAO = contactDAO;
    }

    public void run() {
        Scanner input = new Scanner(System.in);

        while (true) {
            contactView.displayMenu();
            String num = contactView.getUserInput("Enter choice: ");

            try {
                switch (num) {
                    case "1": {
                        String name = contactView.getUserInput("Enter name: ");
                        String phoneNumber = contactView.getUserInput("Enter number: ");
                        Contact contact = new Contact(name, phoneNumber);
                        contactDAO.save(contact);
                        contactView.displayMessage("Contact saved.");
                        break;
                    }
                    case "2": {
                        String name = contactView.getUserInput("Enter name: ");
                        Contact found = contactDAO.findByName(name);
                        if (found == null) {
                            contactView.displayError("Contact not found.");
                        } else {
                            contactView.displayContacts(List.of(found));
                        }
                        break;
                    }
                    case "3": {
                        List<Contact> contacts = contactDAO.findAll();
                        if (contacts.isEmpty()) {
                            contactView.displayError("Contact list is empty!");
                        } else {
                            contactView.displayContacts(contacts);
                        }
                        break;
                    }
                    case "4": {
                        return;
                    }
                    default: {
                        contactView.displayError("Wrong choice! Please enter 1-4.");
                        break;
                    }
                }
            } catch (ContactStorageException e) {
                ExceptionHandler.handle(e);
            } catch (DuplicateContactException e) {
                ExceptionHandler.handle(e);
            } catch (IllegalArgumentException e) {
                contactView.displayError(e.getMessage());
            }
        }
    }
}
