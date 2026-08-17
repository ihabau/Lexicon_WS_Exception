![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Library Book Management

## Class Diagram

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

## Checklist

- [x] Task 1: Create the `Book` class in the `model` package with validation for fields and ISBN format
- [x] Task 2: Define `BookStorageException`, `DuplicateBookException`, and `BookNotAvailableException` in the `exception` package
- [x] Task 3: Implement `BookDAO` and `FileBookDAOImpl` in the `data` package
- [x] Task 4: Create the `BookView` and `BookController` following the MVC pattern
- [x] Task 5: Explain the MVC design pattern

See [Workshop_1_Library_Books.md](Workshop_1_Library_Books.md) for full instructions.
