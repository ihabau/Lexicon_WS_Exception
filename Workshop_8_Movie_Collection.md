![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Movie Collection Manager

## Objective
Build a Java application to manage a movie collection stored in a text file using advanced exception handling techniques.

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
    *   **Artifact Id:** `movie-collection-workshop`
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

---

## 1: The Model & Validation (Unchecked)
**Task:** Create the `Movie` class in the `model` package.

*   **Validation:** Validate fields in the setters and use in the constructor, throw `IllegalArgumentException` if the input is invalid (title must not be blank, director must not be blank, releaseYear must be >= 1888, rating must be between 0.0 and 10.0).

## 2: Custom Exceptions (Checked)
**Task:** Define `MovieStorageException`, `DuplicateMovieException`, and `InvalidRatingException` in the `exception` package.

## 3: The Data Layer (DAO)
**Task:** Implement `MovieDAO` and `FileMovieDAOImpl` in the `data` package.

*   **Responsibility:** The DAO is strictly for data persistence. It should **never** print to the console. It only communicates through return values or **Exceptions**.
*   **File Format:** Each line in `movies.txt` should be: `title,director,releaseYear,rating`

## 4: The View & Controller (MVC)
**Task:** Create the `MovieView` (in `view` package) and `MovieController` (in `controller` package).

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
Add a **filter by rating** feature. Allow the user to display only movies that meet or exceed a specified minimum rating.
