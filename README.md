![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Movie Collection Manager

## Class Diagram

```mermaid
classDiagram
    namespace model {
        class Movie {
            -String title
            -String director
            -int releaseYear
            -double rating
            +Movie(String title, String director, int releaseYear, double rating)
        }
    }

    namespace data {
        class MovieDAO {
            <<interface>>
            +findAll() List~Movie~
            +save(Movie movie) void
            +findByTitle(String title) Movie
        }
        class FileMovieDAOImpl {
            -Path filePath
        }
    }

    namespace view {
        class MovieView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayMovies(List~Movie~ movies) void
            +displayMessage(String message) void
            +displayError(String message) void
        }
    }

    namespace controller {
        class MovieController {
            -MovieDAO movieDAO
            -MovieView movieView
            +run() void
        }
    }

    namespace exception {
        class MovieStorageException { }
        class DuplicateMovieException { }
        class InvalidRatingException { }
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
    }

    MovieDAO <|.. FileMovieDAOImpl
    MovieController --> MovieDAO : uses
    MovieController --> MovieView : updates
    MovieController ..> ExceptionHandler : delegates errors

    MovieDAO ..> Movie : manages
    FileMovieDAOImpl ..> Movie : persists

    Movie ..> IllegalArgumentException : throws
    Movie ..> InvalidRatingException : throws
    FileMovieDAOImpl ..> MovieStorageException : throws
    FileMovieDAOImpl ..> DuplicateMovieException : throws
```

## Checklist

- [ ] Task 1: Create the `Movie` class in the `model` package with validation (title, director, releaseYear, rating)
- [ ] Task 2: Define `MovieStorageException`, `DuplicateMovieException`, and `InvalidRatingException` in the `exception` package
- [ ] Task 3: Implement `MovieDAO` and `FileMovieDAOImpl` in the `data` package
- [ ] Task 4: Create the `MovieView` and `MovieController` following the MVC pattern
- [ ] Task 5: Explain the MVC design pattern

See [Workshop_8_Movie_Collection.md](Workshop_8_Movie_Collection.md) for full instructions.
