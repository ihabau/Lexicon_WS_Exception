![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Product Inventory System

> **Step 3 of 10** — Difficulty **3/10** (10 hints provided)
> Focus: the `dao` interface + `daoImpl` file persistence — like `se.lexicon.dao` + `*Impl` in the Event Manager app.

## The 10-step path to a full Event-Manager-style application

| Step | Branch | Scenario | Event-Manager part(s) | Difficulty |
|:---:|---|:---:|---|:---:|
| 1 | workshop-1-library-books | Library Book Management | `model` + validation | 1/10 |
| 2 | workshop-2-employee-mgmt | Employee Management | `ui` + `controller` (MVC) | 2/10 |
| **3** | **workshop-3-product-inventory** | **Product Inventory** | **`dao` + `daoImpl` (file)** | **3/10** |
| 4 | workshop-4-student-grades | Student Grades | `exception` + ExceptionHandler | 4/10 |
| 5 | workshop-5-task-manager | Task Manager | enum + streams + 2 entities | 5/10 |
| 6 | workshop-6-recipe-manager | Recipe Manager | `service` layer | 6/10 |
| 7 | workshop-7-vehicle-registration | Vehicle Registration | `utility` + SQL schema + first JDBC DAO | 7/10 |
| 8 | workshop-8-movie-collection | Movie Collection | full JDBC `daoImpl` CRUD | 8/10 |
| 9 | workshop-9-bank-account | Bank Account | relations + transactions + base exception | 9/10 |
| 10 | workshop-10-pet-adoption | Pet Adoption | everything (full clone) | 10/10 |

Each branch keeps its own scenario, adds a little more of the architecture, and gives **fewer hints**.

## This step — the DAO layer

```mermaid
classDiagram
    namespace data {
        class ProductDAO {
            <<interface>>
            +findAll() List~Product~
            +save(Product product) void
            +findByBarcode(String barcode) Product
        }
        class FileProductDAOImpl {
            -List~Product~ products
            -void loadFromFile()
        }
    }
    namespace exception {
        class ProductStorageException {
            <<checked>>
        }
    }
    ProductDAO <|.. FileProductDAOImpl
    FileProductDAOImpl ..> ProductStorageException : throws
```

## Checklist

- [ ] Task 1: `Product` model with validation (name, price > 0, quantity >= 0, barcode `^\d{8}$`)
- [ ] Task 2: Checked exceptions `ProductStorageException`, `DuplicateProductException`
- [ ] Task 3: `ProductDAO` interface
- [ ] Task 4: `FileProductDAOImpl` — file persistence, try-with-resources, no console printing
- [ ] Task 5: Controller depends on the interface; data survives restart
- [ ] Task 6: Explain why the controller uses the interface

## Running

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="se.lexicon.Main"
```

See [Workshop_3_Product_Inventory.md](Workshop_3_Product_Inventory.md) for the full instructions, flowcharts and test scenarios.