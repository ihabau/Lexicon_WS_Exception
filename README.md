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

- [ ] Task 1: `Product` model with validation + barcode regex (`^\d{8}$`)
- [ ] Task 2: Custom checked exceptions (`ProductStorageException`, `DuplicateProductException`, `InsufficientStockException`)
- [ ] Task 3: DAO layer (`ProductDAO`, `FileProductDAOImpl`) - no console printing
- [ ] Task 4: View & Controller (MVC), try-catch loop in controller
- [ ] Task 5: Explain the MVC design pattern

See [Workshop_3_Product_Inventory.md](Workshop_3_Product_Inventory.md) for full instructions.
