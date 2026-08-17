![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Bank Account Manager

## Class Diagram

```mermaid
classDiagram
    namespace model {
        class Account {
            -String ownerName
            -String accountNumber
            -double balance
            +Account(String ownerName, String accountNumber, double balance)
        }
    }

    namespace data {
        class AccountDAO {
            <<interface>>
            +findAll() List~Account~
            +save(Account account) void
            +findByAccountNumber(String accountNumber) Account
        }
        class FileAccountDAOImpl {
            -Path filePath
        }
    }

    namespace view {
        class AccountView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayAccounts(List~Account~ accounts) void
            +displayMessage(String message) void
            +displayError(String message) void
        }
    }

    namespace controller {
        class AccountController {
            -AccountDAO accountDAO
            -AccountView accountView
            +run() void
        }
    }

    namespace exception {
        class AccountStorageException { }
        class DuplicateAccountException { }
        class InsufficientFundsException { }
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
    }

    AccountDAO <|.. FileAccountDAOImpl
    AccountController --> AccountDAO : uses
    AccountController --> AccountView : updates
    AccountController ..> ExceptionHandler : delegates errors

    AccountDAO ..> Account : manages
    FileAccountDAOImpl ..> Account : persists

    Account ..> IllegalArgumentException : throws
    Account ..> InsufficientFundsException : throws
    FileAccountDAOImpl ..> AccountStorageException : throws
    FileAccountDAOImpl ..> DuplicateAccountException : throws
```

## Checklist

- [ ] Task 1: Create the `Account` class in the `model` package with validation (ownerName, balance, accountNumber)
- [ ] Task 2: Define `AccountStorageException`, `DuplicateAccountException`, and `InsufficientFundsException` in the `exception` package
- [ ] Task 3: Implement `AccountDAO` and `FileAccountDAOImpl` in the `data` package
- [ ] Task 4: Create the `AccountView` and `AccountController` following the MVC pattern
- [ ] Task 5: Explain the MVC design pattern

See [Workshop_9_Bank_Account.md](Workshop_9_Bank_Account.md) for full instructions.
