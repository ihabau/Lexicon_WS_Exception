package se.lexicon.view;

import se.lexicon.model.Contact;
import java.util.List;
import java.util.Scanner;

// TODO: the workshop says the View handles ALL user interaction via Scanner and System.out - remove javax.swing.*

public class ContactView {

    // what is the prompt for?
    public String getUserInput(String prompt) {
        Scanner input = new Scanner(System.in);

        System.out.print("> ");
        String message = input.next();

        return message;
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
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String message) {
    }

}
