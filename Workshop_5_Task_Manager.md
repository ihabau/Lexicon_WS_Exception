![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Task/Todo Manager

## Objective
Build a Java application to manage tasks stored in a text file using advanced exception handling techniques.

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
    *   **Artifact Id:** `task-manager-workshop`
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
        class Task {
            -String title
            -String description
            -boolean completed
            +Task(String title, String description)
        }
    }

    namespace data {
        class TaskDAO {
            <<interface>>
            +findAll() List~Task~
            +save(Task task) void
            +findByTitle(String title) Task
        }
        class FileTaskDAOImpl {
            -Path filePath
        }
    }

    namespace view {
        class TaskView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayTasks(List~Task~ tasks) void
            +displayMessage(String message) void
            +displayError(String message) void
        }
    }

    namespace controller {
        class TaskController {
            -TaskDAO taskDAO
            -TaskView taskView
            +run() void
        }
    }

    namespace exception {
        class TaskStorageException { }
        class DuplicateTaskException { }
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
    }

    TaskDAO <|.. FileTaskDAOImpl
    TaskController --> TaskDAO : uses
    TaskController --> TaskView : updates
    TaskController ..> ExceptionHandler : delegates errors

    TaskDAO ..> Task : manages
    FileTaskDAOImpl ..> Task : persists

    Task ..> IllegalArgumentException : throws
    FileTaskDAOImpl ..> TaskStorageException : throws
    FileTaskDAOImpl ..> DuplicateTaskException : throws
```

---

## 1: The Model & Validation (Unchecked)
**Task:** Create the `Task` class in the `model` package.

*   **Validation:** for fields in the setters and use in constructor, throw `IllegalArgumentException` if the input is invalid.
*   **Title Validation:** Title must not be blank.
*   **Description Validation:** Description must not be blank.
*   **Completed Field:** Default to `false` when a new task is created.

## 2: Custom Exceptions (Checked)
**Task:** Define `TaskStorageException` and `DuplicateTaskException` in the `exception` package.

## 3: The Data Layer (DAO)
**Task:** Implement `TaskDAO` and `FileTaskDAOImpl` in the `data` package.

*   **Responsibility:** The DAO is strictly for data persistence. It should **never** print to the console. It only communicates through return values or **Exceptions**.
*   **File Format:** Each line in `tasks.txt` should be: `title,description,completed`

## 4: The View & Controller (MVC)
**Task:** Create the `TaskView` (in `view` package) and `TaskController` (in `controller` package).

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
Add a **mark-complete** and **delete** feature. When a task is marked complete, set `completed` to `true`. Allow the user to delete a task by its title. If the task is not found, throw an appropriate exception.