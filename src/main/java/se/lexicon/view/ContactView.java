package se.lexicon.view;

import se.lexicon.model.Contact;
import java.util.List;
import java.util.Scanner;

public class ContactView {

    private final Scanner input = new Scanner(System.in);

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    public void displayMenu() {
        System.out.print("""
                ------------CONTACTS------------
                1: Add
                2: Find
                3: Display All
                4: Exit
                --------------------------------
                """);
    }

    public void displayContacts(List<Contact> contacts) {
        contacts.forEach(contact -> {
            System.out.println("Name: " + contact.getName() + " | Phone: " + contact.getPhoneNumber());
        });
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String message) {
        System.out.println("ERROR: " + message);
    }
}
