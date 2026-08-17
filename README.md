![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Product Inventory System

## Class Diagram

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

## Checklist

- [x] Task 1: Create the `Product` class in the `model` package with validation for fields, name, price, quantity, and barcode
- [x] Task 2: Define `ProductStorageException`, `DuplicateProductException`, and `InsufficientStockException` in the `exception` package
- [x] Task 3: Implement `ProductDAO` and `FileProductDAOImpl` in the `data` package
- [x] Task 4: Create the `ProductView` and `ProductController` following the MVC pattern
- [x] Task 5: Explain the MVC design pattern

See [Workshop_3_Product_Inventory.md](Workshop_3_Product_Inventory.md) for full instructions.
