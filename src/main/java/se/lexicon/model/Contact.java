package se.lexicon.model;

public class Contact {

    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid name!");
        }

        if (phoneNumber == null || !phoneNumber.matches("^\\d{10}$")) {
            throw new IllegalArgumentException("Invalid number!");
        }

        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    //setters
    public void setName(String name) {
        if ( name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid name!");
        }
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^\\d{10}$")) {
            throw new IllegalArgumentException("Invalid number!");
        }
        this.phoneNumber = phoneNumber;
    }
}