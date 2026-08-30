![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Student Grade Tracker

> **Step 4 of 10** — Difficulty **4/10** (9 hints provided)
> Focus: the `exception` layer — custom exceptions + centralized `ExceptionHandler` — like `se.lexicon.exception` in the Event Manager app.

## The 10-step path to a full Event-Manager-style application

| Step | Branch | Scenario | Event-Manager part(s) | Difficulty |
|:---:|---|:---:|---|:---:|
| 1 | workshop-1-library-books | Library Book Management | `model` + validation | 1/10 |
| 2 | workshop-2-employee-mgmt | Employee Management | `ui` + `controller` (MVC) | 2/10 |
| 3 | workshop-3-product-inventory | Product Inventory | `dao` + `daoImpl` (file) | 3/10 |
| **4** | **workshop-4-student-grades** | **Student Grades** | **`exception` + ExceptionHandler** | **4/10** |
| 5 | workshop-5-task-manager | Task Manager | enum + streams + 2 entities | 5/10 |
| 6 | workshop-6-recipe-manager | Recipe Manager | `service` layer | 6/10 |
| 7 | workshop-7-vehicle-registration | Vehicle Registration | `utility` + SQL schema + first JDBC DAO | 7/10 |
| 8 | workshop-8-movie-collection | Movie Collection | full JDBC `daoImpl` CRUD | 8/10 |
| 9 | workshop-9-bank-account | Bank Account | relations + transactions + base exception | 9/10 |
| 10 | workshop-10-pet-adoption | Pet Adoption | everything (full clone) | 10/10 |

Each branch keeps its own scenario, adds a little more of the architecture, and gives **fewer hints**.

## This step — exceptions

```mermaid
classDiagram
    class StudentException {
        <<checked base>>
    }
    class InvalidGradeException
    class DuplicateStudentException
    class StudentStorageException
    class ExceptionHandler {
        +handle(Exception e, StudentView view)$ void
    }
    StudentException <|-- InvalidGradeException
    StudentException <|-- DuplicateStudentException
    StudentException <|-- StudentStorageException
    ExceptionHandler ..> StudentException : prints message
```

## Checklist

- [ ] Task 1: `Student` throws `InvalidGradeException` for grades outside 0–100
- [ ] Task 2: Exception family — `StudentException` base + 3 subclasses
- [ ] Task 3: DAO wraps checked `IOException` as `StudentStorageException`
- [ ] Task 4: `ExceptionHandler` is the single place errors become messages
- [ ] Task 5: Controller: one try/catch per action → handler
- [ ] Task 6: Explain checked vs unchecked choice

## Running

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="se.lexicon.Main"
```

See [Workshop_4_Student_Grades.md](Workshop_4_Student_Grades.md) for the full instructions, flowcharts and test scenarios.