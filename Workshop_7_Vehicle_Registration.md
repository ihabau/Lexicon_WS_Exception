![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Vehicle Registration System

## Objective
Build a Java application to manage vehicle registrations stored in a text file using advanced exception handling techniques.

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
    *   **Artifact Id:** `vehicle-registration-workshop`
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
        class Vehicle {
            -String make
            -String model
            -int year
            -String licensePlate
            +Vehicle(String make, String model, int year, String licensePlate)
        }
    }

    namespace data {
        class VehicleDAO {
            <<interface>>
            +findAll() List~Vehicle~
            +save(Vehicle vehicle) void
            +findByLicensePlate(String licensePlate) Vehicle
        }
        class FileVehicleDAOImpl {
            -Path filePath
        }
    }

    namespace view {
        class VehicleView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayVehicles(List~Vehicle~ vehicles) void
            +displayMessage(String message) void
            +displayError(String message) void
        }
    }

    namespace controller {
        class VehicleController {
            -VehicleDAO vehicleDAO
            -VehicleView vehicleView
            +run() void
        }
    }

    namespace exception {
        class VehicleStorageException { }
        class DuplicateVehicleException { }
        class InvalidYearException { }
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
    }

    VehicleDAO <|.. FileVehicleDAOImpl
    VehicleController --> VehicleDAO : uses
    VehicleController --> VehicleView : updates
    VehicleController ..> ExceptionHandler : delegates errors

    VehicleDAO ..> Vehicle : manages
    FileVehicleDAOImpl ..> Vehicle : persists

    Vehicle ..> IllegalArgumentException : throws
    Vehicle ..> InvalidYearException : throws
    FileVehicleDAOImpl ..> VehicleStorageException : throws
    FileVehicleDAOImpl ..> DuplicateVehicleException : throws
```

---

## 1: The Model & Validation (Unchecked)
**Task:** Create the `Vehicle` class in the `model` package.

*   **Validation:** Validate fields in the setters and use in the constructor, throw `IllegalArgumentException` if the input is invalid (make must not be blank, model must not be blank, year must be >= 1886).
*   **License Plate Validation:** Validate the license plate format using a Regular Expression (e.g., `^[A-Z]{3}-\\d{3}$`).

## 2: Custom Exceptions (Checked)
**Task:** Define `VehicleStorageException`, `DuplicateVehicleException`, and `InvalidYearException` in the `exception` package.

## 3: The Data Layer (DAO)
**Task:** Implement `VehicleDAO` and `FileVehicleDAOImpl` in the `data` package.

*   **Responsibility:** The DAO is strictly for data persistence. It should **never** print to the console. It only communicates through return values or **Exceptions**.
*   **File Format:** Each line in `vehicles.txt` should be: `make,model,year,licensePlate`

## 4: The View & Controller (MVC)
**Task:** Create the `VehicleView` (in `view` package) and `VehicleController` (in `controller` package).

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
Add a **search by year range** feature. Allow the user to filter and display all vehicles registered between a given start year and end year.
