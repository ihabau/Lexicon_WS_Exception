![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Student Grade Tracker

## Objective
Build a Java application to manage student grades stored in a text file using advanced exception handling techniques.

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
    *   **Artifact Id:** `student-grade-workshop`
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
        class Student {
            -String name
            -String studentId
            -double grade
            +Student(String name, String studentId, double grade)
        }
    }

    namespace data {
        class StudentDAO {
            <<interface>>
            +findAll() List~Student~
            +save(Student student) void
            +findByStudentId(String studentId) Student
        }
        class FileStudentDAOImpl {
            -Path filePath
        }
    }

    namespace view {
        class StudentView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayStudents(List~Student~ students) void
            +displayMessage(String message) void
            +displayError(String message) void
        }
    }

    namespace controller {
        class StudentController {
            -StudentDAO studentDAO
            -StudentView studentView
            +run() void
        }
    }

    namespace exception {
        class StudentStorageException { }
        class DuplicateStudentException { }
        class InvalidGradeException { }
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
    }

    StudentDAO <|.. FileStudentDAOImpl
    StudentController --> StudentDAO : uses
    StudentController --> StudentView : updates
    StudentController ..> ExceptionHandler : delegates errors

    StudentDAO ..> Student : manages
    FileStudentDAOImpl ..> Student : persists

    Student ..> IllegalArgumentException : throws
    Student ..> InvalidGradeException : throws
    FileStudentDAOImpl ..> StudentStorageException : throws
    FileStudentDAOImpl ..> DuplicateStudentException : throws
```

---

## 1: The Model & Validation (Unchecked)
**Task:** Create the `Student` class in the `model` package.

*   **Validation:** for fields in the setters and use in constructor, throw `IllegalArgumentException` if the input is invalid.
*   **Name Validation:** Name must not be blank.
*   **Student ID Validation:** Student ID must match the pattern `^S\d{6}$` (e.g., `S123456`).
*   **Grade Validation:** Grade must be between 0.0 and 100.0 (inclusive). Throw `InvalidGradeException` if the grade is outside this range.

## 2: Custom Exceptions (Checked)
**Task:** Define `StudentStorageException`, `DuplicateStudentException`, and `InvalidGradeException` in the `exception` package.

## 3: The Data Layer (DAO)
**Task:** Implement `StudentDAO` and `FileStudentDAOImpl` in the `data` package.

*   **Responsibility:** The DAO is strictly for data persistence. It should **never** print to the console. It only communicates through return values or **Exceptions**.
*   **File Format:** Each line in `students.txt` should be: `name,studentId,grade`

## 4: The View & Controller (MVC)
**Task:** Create the `StudentView` (in `view` package) and `StudentController` (in `controller` package).

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
Add a **GPA calculation** and **letter grade display** feature. Calculate the average grade across all students and display each student's letter grade (A: 90-100, B: 80-89, C: 70-79, D: 60-69, F: 0-59).