![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Vehicle Registration System

## Class Diagram

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

## Checklist

- [x] Task 1: Create the `Vehicle` class in the `model` package with validation (make, model, year, licensePlate)
- [x] Task 2: Define `VehicleStorageException`, `DuplicateVehicleException`, and `InvalidYearException` in the `exception` package
- [x] Task 3: Implement `VehicleDAO` and `FileVehicleDAOImpl` in the `data` package
- [x] Task 4: Create the `VehicleView` and `VehicleController` following the MVC pattern
- [x] Task 5: Explain the MVC design pattern

See [Workshop_7_Vehicle_Registration.md](Workshop_7_Vehicle_Registration.md) for full instructions.
