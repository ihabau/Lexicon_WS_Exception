![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Product Inventory System

## Objective
Build a Java application to manage product inventory stored in a text file using advanced exception handling techniques.

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
    *   **Artifact Id:** `product-inventory-workshop`
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
        class Product {
            -String name
            -double price
            -int quantity
            -String barcode
            +Product(String name, double price, int quantity, String barcode)
        }
    }

    namespace data {
        class ProductDAO {
            <<interface>>
            +findAll() List~Product~
            +save(Product product) void
            +findByBarcode(String barcode) Product
        }
        class FileProductDAOImpl {
            -Path filePath
        }
    }

    namespace view {
        class ProductView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayProducts(List~Product~ products) void
            +displayMessage(String message) void
            +displayError(String message) void
        }
    }

    namespace controller {
        class ProductController {
            -ProductDAO productDAO
            -ProductView productView
            +run() void
        }
    }

    namespace exception {
        class ProductStorageException { }
        class DuplicateProductException { }
        class InsufficientStockException { }
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
    }

    ProductDAO <|.. FileProductDAOImpl
    ProductController --> ProductDAO : uses
    ProductController --> ProductView : updates
    ProductController ..> ExceptionHandler : delegates errors

    ProductDAO ..> Product : manages
    FileProductDAOImpl ..> Product : persists

    Product ..> IllegalArgumentException : throws
    Product ..> InsufficientStockException : throws
    FileProductDAOImpl ..> ProductStorageException : throws
    FileProductDAOImpl ..> DuplicateProductException : throws
```

---

## 1: The Model & Validation (Unchecked)
**Task:** Create the `Product` class in the `model` package.

*   **Validation:** for fields in the setters and use in constructor, throw `IllegalArgumentException` if the input is invalid.
*   **Name Validation:** Name must not be blank.
*   **Price Validation:** Price must be greater than 0.
*   **Quantity Validation:** Quantity must be greater than or equal to 0.
*   **Barcode Validation:** Barcode must match the pattern `^\d{8}$` (exactly 8 digits).

## 2: Custom Exceptions (Checked)
**Task:** Define `ProductStorageException`, `DuplicateProductException`, and `InsufficientStockException` in the `exception` package.

## 3: The Data Layer (DAO)
**Task:** Implement `ProductDAO` and `FileProductDAOImpl` in the `data` package.

*   **Responsibility:** The DAO is strictly for data persistence. It should **never** print to the console. It only communicates through return values or **Exceptions**.
*   **File Format:** Each line in `products.txt` should be: `name,price,quantity,barcode`

## 4: The View & Controller (MVC)
**Task:** Create the `ProductView` (in `view` package) and `ProductController` (in `controller` package).

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
Add a **purchase** feature that reduces stock. When a product is purchased, decrease the quantity by the purchased amount. Throw `InsufficientStockException` if the requested quantity exceeds the available stock.