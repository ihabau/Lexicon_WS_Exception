![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Exception and Files - README Checklist

> Checklist of topics to cover in the README, based on `Exception_Preseentation.md` and `Exception_Workshop.md`.

## MVC Design
MVC stands for Model, View and Controller.
- **Model** (`Contact`)
    The core data structure and business rules. It defines what the data looks like
    and validates it (e.g. name not blank, phone must be 10 digits). It knows nothing
    about files, UI, or how it's stored.
- **View** (`ContactView`)
    The interface the user interacts with. Handles all input (Scanner) and output
    (System.out). It knows nothing about business logic or file storage.
- **Controller** (`ContactController`)
    The coordinator between Model and View. It reads user input from the View,
    calls the right DAO method, catches exceptions, and tells the View what to
    display. It contains the try-catch loop and is the only layer that decides
    what to do with errors.

## Project Overview
- [x] Project title and short description
- [x] Purpose of the repository (contains presentation + workshop materials)
- [x] Link to [`Exception_Preseentation.md`](Exception_Preseentation.md) (theory/slides)
- [x] Link to [`Exception_Workshop.md`](Exception_Workshop.md) (hands-on tasks)

## Theory Topics (from the Presentation)
- [x] Introduction to exceptions (what, why, advantages)
- [x] Types of exceptions: Checked, Unchecked, Errors
- [x] Exception hierarchy (`Throwable`, `Exception`, `RuntimeException`, `Error`)
- [x] Handling exceptions: `try`, `catch`, `finally`
- [x] `try-with-resources` and automatic resource closing
- [x] Execution flow summary (try -> catch -> finally)
- [x] `throw` vs `throws` and when to use each
- [x] Custom (user-defined) exceptions and why to use them

## Workshop: Contact App (from the Workshop)
- [x] Workshop objective (manage contacts stored in a text file)
- [x] Learning goals (validation, custom exceptions, try-with-resources, centralized handler)
- [x] Prerequisites & setup (Maven project: `se.lexicon` / `contact-app-workshop`)
- [x] Git init, push to GitHub/GitLab, share link with instructor
- [x] Class diagram / package structure (model, data, view, controller, exception)

```mermaid
classDiagram
    namespace model {
        class Contact {
            -String name
            -String phoneNumber
            +Contact(String name, String phoneNumber)
        }
    }

    namespace data {
        class ContactDAO {
            <<interface>>
            +findAll() List~Contact~
            +save(Contact contact) void
            +findByName(String name) Contact
        }
        class FileContactDAOImpl {
            -Path filePath
        }
    }

    namespace view {
        class ContactView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayContacts(List~Contact~ contacts) void
            +displayMessage(String message) void
            +displayError(String message) void
        }
    }

    namespace controller {
        class ContactController {
            -ContactDAO contactDAO
            -ContactView contactView
            +run() void
        }
    }

    namespace exception {
        class ContactStorageException { }
        class DuplicateContactException { }
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
    }

    ContactDAO <|.. FileContactDAOImpl
    ContactController --> ContactDAO : uses
    ContactController --> ContactView : updates
    ContactController ..> ExceptionHandler : delegates errors

    %% Package Dependencies (MVC Flow)
    ContactDAO ..> Contact : manages
    FileContactDAOImpl ..> Contact : persists

    %% Exceptions
    Contact ..> IllegalArgumentException : throws
    FileContactDAOImpl ..> ContactStorageException : throws
    FileContactDAOImpl ..> DuplicateContactException : throws
```
- [x] Task 1: `Contact` model with validation + phone regex (`^\d{10}`)
- [x] Task 2: Custom checked exceptions (`ContactStorageException`, `DuplicateContactException`)
- [x] Task 3: DAO layer (`ContactDAO`, `FileContactDAOImpl`) - no console printing
- [x] Task 4: View & Controller (MVC), try-catch loop in controller
- [x] Task 5: Explain the MVC design pattern

## Submission & Grading
- [x] How to run the application
- [x] Where to submit (repository link)
- [x] Checklist of completed tasks

## Miscellaneous
- [x] Built with / Tech stack (Java, Maven)
- [ ] License (optional)
- [x] Authors / instructor name (optional)
