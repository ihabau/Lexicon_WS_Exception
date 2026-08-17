package se.lexicon;

import se.lexicon.controller.ContactController;
import se.lexicon.data.ContactDAO;
import se.lexicon.data.FileContactDAOImpl;
import se.lexicon.view.ContactView;

public class Main {
    public static void main(String[] args) {

        ContactView view = new ContactView();
        ContactDAO dao = new FileContactDAOImpl();
        ContactController controller = new ContactController(view, dao);

        controller.run();

    }
}
