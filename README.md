![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Employee Management System

## Class Diagram

```mermaid
classDiagram
    namespace model {
        class Employee {
            -String name
            -String employeeId
            -String department
            -double salary
            +Employee(String name, String employeeId, String department, double salary)
        }
    }

    namespace data {
        class EmployeeDAO {
            <<interface>>
            +findAll() List~Employee~
            +save(Employee employee) void
            +findByEmployeeId(String employeeId) Employee
        }
        class FileEmployeeDAOImpl {
            -Path filePath
        }
    }

    namespace view {
        class EmployeeView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayEmployees(List~Employee~ employees) void
            +displayMessage(String message) void
            +displayError(String message) void
        }
    }

    namespace controller {
        class EmployeeController {
            -EmployeeDAO employeeDAO
            -EmployeeView employeeView
            +run() void
        }
    }

    namespace exception {
        class EmployeeStorageException { }
        class DuplicateEmployeeException { }
        class InvalidSalaryException { }
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
    }

    EmployeeDAO <|.. FileEmployeeDAOImpl
    EmployeeController --> EmployeeDAO : uses
    EmployeeController --> EmployeeView : updates
    EmployeeController ..> ExceptionHandler : delegates errors

    EmployeeDAO ..> Employee : manages
    FileEmployeeDAOImpl ..> Employee : persists

    Employee ..> IllegalArgumentException : throws
    Employee ..> InvalidSalaryException : throws
    FileEmployeeDAOImpl ..> EmployeeStorageException : throws
    FileEmployeeDAOImpl ..> DuplicateEmployeeException : throws
```

## Checklist

- [ ] Task 1: Create the `Employee` class in the `model` package with validation for fields, name, employee ID, and salary
- [ ] Task 2: Define `EmployeeStorageException`, `DuplicateEmployeeException`, and `InvalidSalaryException` in the `exception` package
- [ ] Task 3: Implement `EmployeeDAO` and `FileEmployeeDAOImpl` in the `data` package
- [ ] Task 4: Create the `EmployeeView` and `EmployeeController` following the MVC pattern
- [ ] Task 5: Explain the MVC design pattern

See [Workshop_2_Employee_Management.md](Workshop_2_Employee_Management.md) for full instructions.
