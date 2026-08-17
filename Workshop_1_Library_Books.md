![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Library Book Management

## Objective
Build a Java application to manage library books stored in a text file using advanced exception handling techniques.

## Learning Goals
*   Implement the application based on the provided Class Diagram.
*   Use **Unchecked Exceptions** for data validation.
*   Create and use **Custom Checked Exceptions**.
*   Apply **Try-with-Resources** for safe File IO.
*   Implement a **Centralized Exception Handler**.

---

## Prerequisites & Submission
**Task:** Setup your environment and prepare for submission.

1.  **Create Maven Project:** Create a new Maven project in your IDE.
    *   **Group Id:** `se.lexicon`
    *   **Artifact Id:** `library-book-workshop`
2.  **Version Control:** Initialize a Git repository for your project and push it to GitHub/GitLab.
3.  **Submission:** Share the link to your repository with your instructor once you have started or completed the workshop.

---

## Conceptual Model (Class Diagram)

The following diagram shows the relationship between the different layers of the application and how exceptions flow through them using the **MVC (Model-View-Controller)** pattern.

### Suggested Package Structure
*   **Model:** `model`
*   **Data:** `data`
*   **View:** `view`
*   **Controller:** `controller`
*   **Exception:** `exception`

```mermaid
classDiagram
    namespace model {
        class Book {
            -String title
            -String author
            -String isbn
            -boolean available
            +Book(String title, String author, String isbn)
        }
    }

    namespace data {
        class BookDAO {
            <<interface>>
            +findAll() List~Book~
            +save(Book book) void
            +findByTitle(String title) Book
        }
        class FileBookDAOImpl {
            -Path filePath
        }
    }

    namespace view {
        class BookView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayBooks(List~Book~ books) void
            +displayMessage(String message) void
            +displayError(String message) void
        }
    }

    namespace controller {
        class BookController {
            -BookDAO bookDAO
            -BookView bookView
            +run() void
        }
    }

    namespace exception {
        class BookStorageException { }
        class DuplicateBookException { }
        class BookNotAvailableException { }
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
    }

    BookDAO <|.. FileBookDAOImpl
    BookController --> BookDAO : uses
    BookController --> BookView : updates
    BookController ..> ExceptionHandler : delegates errors

    BookDAO ..> Book : manages
    FileBookDAOImpl ..> Book : persists

    Book ..> IllegalArgumentException : throws
    FileBookDAOImpl ..> BookStorageException : throws
    FileBookDAOImpl ..> DuplicateBookException : throws
    Book ..> BookNotAvailableException : throws
```

---

## 1: The Model & Validation (Unchecked)
**Task:** Create the `Book` class in the `model` package.

*   **Validation:** for fields in the setters and use in constructor, throw `IllegalArgumentException` if the input is invalid (title and author must not be blank).
*   **ISBN Validation:** Validate the ISBN format using a Regular Expression (e.g., `^\\d{13}$` for 13-digit ISBNs).
*   **Available Field:** Default to `true` when a new book is created.

## 2: Custom Exceptions (Checked)
**Task:** Define `BookStorageException`, `DuplicateBookException`, and `BookNotAvailableException` in the `exception` package.

## 3: The Data Layer (DAO)
**Task:** Implement `BookDAO` and `FileBookDAOImpl` in the `data` package.

*   **Responsibility:** The DAO is strictly for data persistence. It should **never** print to the console. It only communicates through return values or **Exceptions**.
*   **File Format:** Each line in `books.txt` should be: `title,author,isbn,available`

## 4: The View & Controller (MVC)
**Task:** Create the `BookView` (in `view` package) and `BookController` (in `controller` package).

*   **The View:** Responsible for all user interaction (`Scanner` and `System.out`).
*   **The Controller:**
    *   Coordinates between the View and the Model.
    *   Contains the `try-catch` loop.
    *   Catches exceptions from the Model/DAO and tells the View what to display.
*   **The App/Main class:** Simply initializes the components and starts the Controller.

## 5: The MVC Design Pattern
**Task:** Explain the MVC (Model-View-Controller) design pattern.

---

## Bonus Challenge
Add a **checkout** and **return** feature. When a book is checked out, set `available` to `false`. Throw `BookNotAvailableException` if someone tries to check out a book that is already unavailable.
