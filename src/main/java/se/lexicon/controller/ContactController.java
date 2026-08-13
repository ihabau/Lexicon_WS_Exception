package se.lexicon.controller;

import se.lexicon.data.ContactDAO;
import se.lexicon.view.ContactView;

public class ContactController {



    private final ContactDAO contactDAO;

    private final ContactView contactView;

    public ContactController(ContactView contactView, ContactDAO contactDAO) {
        this.contactView = contactView;
        this.contactDAO = contactDAO;
    }

    public void run() {

    }

}
