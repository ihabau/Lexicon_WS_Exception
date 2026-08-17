![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Student Grade Tracker

## Class Diagram

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

## Checklist

- [ ] Task 1: `Student` model with validation + studentId regex (`^S\d{6}$`)
- [ ] Task 2: Custom checked exceptions (`StudentStorageException`, `DuplicateStudentException`, `InvalidGradeException`)
- [ ] Task 3: DAO layer (`StudentDAO`, `FileStudentDAOImpl`) - no console printing
- [ ] Task 4: View & Controller (MVC), try-catch loop in controller
- [ ] Task 5: Explain the MVC design pattern

See [Workshop_4_Student_Grades.md](Workshop_4_Student_Grades.md) for full instructions.
