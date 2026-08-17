![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Pet Adoption System

## Class Diagram

```mermaid
classDiagram
    namespace model {
        class Pet {
            -String name
            -String species
            -int age
            -boolean adopted
            +Pet(String name, String species, int age)
        }
    }

    namespace data {
        class PetDAO {
            <<interface>>
            +findAll() List~Pet~
            +save(Pet pet) void
            +findByName(String name) Pet
        }
        class FilePetDAOImpl {
            -Path filePath
        }
    }

    namespace view {
        class PetView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayPets(List~Pet~ pets) void
            +displayMessage(String message) void
            +displayError(String message) void
        }
    }

    namespace controller {
        class PetController {
            -PetDAO petDAO
            -PetView petView
            +run() void
        }
    }

    namespace exception {
        class PetStorageException { }
        class DuplicatePetException { }
        class PetAlreadyAdoptedException { }
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
    }

    PetDAO <|.. FilePetDAOImpl
    PetController --> PetDAO : uses
    PetController --> PetView : updates
    PetController ..> ExceptionHandler : delegates errors

    PetDAO ..> Pet : manages
    FilePetDAOImpl ..> Pet : persists

    Pet ..> IllegalArgumentException : throws
    Pet ..> PetAlreadyAdoptedException : throws
    FilePetDAOImpl ..> PetStorageException : throws
    FilePetDAOImpl ..> DuplicatePetException : throws
```

## Checklist

- [x] Task 1: Create the `Pet` class in the `model` package with validation (name, species, age, adopted)
- [x] Task 2: Define `PetStorageException`, `DuplicatePetException`, and `PetAlreadyAdoptedException` in the `exception` package
- [x] Task 3: Implement `PetDAO` and `FilePetDAOImpl` in the `data` package
- [x] Task 4: Create the `PetView` and `PetController` following the MVC pattern
- [x] Task 5: Explain the MVC design pattern

See [Workshop_10_Pet_Adoption.md](Workshop_10_Pet_Adoption.md) for full instructions.
