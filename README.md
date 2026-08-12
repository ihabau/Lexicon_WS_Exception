![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Exception and Files - README Checklist

> Checklist of topics to cover in the README, based on `Exception_Preseentation.md` and `Exception_Workshop.md`.


## Project Overview
- [x] Project title and short description
- [x] Purpose of the repository (contains presentation + workshop materials)
- [x] Link to [`Exception_Preseentation.md`](Exception_Preseentation.md) (theory/slides)
- [x] Link to [`Exception_Workshop.md`](Exception_Workshop.md) (hands-on tasks)

## Theory Topics (from the Presentation)
- [ ] Introduction to exceptions (what, why, advantages)
- [ ] Types of exceptions: Checked, Unchecked, Errors
- [ ] Exception hierarchy (`Throwable`, `Exception`, `RuntimeException`, `Error`)
- [ ] Handling exceptions: `try`, `catch`, `finally`
- [ ] `try-with-resources` and automatic resource closing
- [ ] Execution flow summary (try -> catch -> finally)
- [ ] `throw` vs `throws` and when to use each
- [ ] Custom (user-defined) exceptions and why to use them

## Workshop: Contact App (from the Workshop)
- [ ] Workshop objective (manage contacts stored in a text file)
- [ ] Learning goals (validation, custom exceptions, try-with-resources, centralized handler)
- [ ] Prerequisites & setup (Maven project: `se.lexicon` / `contact-app-workshop`)
- [ ] Git init, push to GitHub/GitLab, share link with instructor
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
- [ ] Task 1: `Contact` model with validation + phone regex (`^\d{10}$`)
- [ ] Task 2: Custom checked exceptions (`ContactStorageException`, `DuplicateContactException`)
- [ ] Task 3: DAO layer (`ContactDAO`, `FileContactDAOImpl`) - no console printing
- [ ] Task 4: View & Controller (MVC), try-catch loop in controller
- [ ] Task 5: Explain the MVC design pattern

## Submission & Grading
- [ ] How to run the application
- [ ] Where to submit (repository link)
- [ ] Checklist of completed tasks

## Miscellaneous
- [ ] Built with / Tech stack (Java, Maven)
- [ ] License (optional)
- [ ] Authors / instructor name (optional)
