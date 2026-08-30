![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Employee Management System

> **Step 2 of 10** — Difficulty **2/10** (11 hints provided)
> Focus: the `ui` + `controller` layers (the MVC split) — like `se.lexicon.ui.AppConsole` in the Event Manager app.

## The 10-step path to a full Event-Manager-style application

| Step | Branch | Scenario | Event-Manager part(s) | Difficulty |
|:---:|---|:---:|---|:---:|
| 1 | workshop-1-library-books | Library Book Management | `model` + validation | 1/10 |
| **2** | **workshop-2-employee-mgmt** | **Employee Management** | **`ui` + `controller` (MVC)** | **2/10** |
| 3 | workshop-3-product-inventory | Product Inventory | `dao` + `daoImpl` (file) | 3/10 |
| 4 | workshop-4-student-grades | Student Grades | `exception` + ExceptionHandler | 4/10 |
| 5 | workshop-5-task-manager | Task Manager | enum + streams + 2 entities | 5/10 |
| 6 | workshop-6-recipe-manager | Recipe Manager | `service` layer | 6/10 |
| 7 | workshop-7-vehicle-registration | Vehicle Registration | `utility` + SQL schema + first JDBC DAO | 7/10 |
| 8 | workshop-8-movie-collection | Movie Collection | full JDBC `daoImpl` CRUD | 8/10 |
| 9 | workshop-9-bank-account | Bank Account | relations + transactions + base exception | 9/10 |
| 10 | workshop-10-pet-adoption | Pet Adoption | everything (full clone) | 10/10 |

Each branch keeps its own scenario, adds a little more of the architecture, and gives **fewer hints**.

## This step — the View & Controller

```mermaid
classDiagram
    namespace model {
        class Employee {
            -String name
            -String employeeId
            -String department
            -double salary
        }
    }
    namespace view {
        class EmployeeView {
            +getUserInput(String prompt) String
            +displayMenu() void
            +displayEmployees(List~Employee~) void
            +displayMessage(String) void
            +displayError(String) void
        }
    }
    namespace controller {
        class EmployeeController {
            -EmployeeView view
            -List~Employee~ employees
            +run() void
        }
    }
    EmployeeController --> EmployeeView : holds
    EmployeeController ..> Employee : uses
```

## Checklist

- [ ] Task 1: `Employee` model with validation (step-1 style)
- [ ] Task 2: `EmployeeView` owns all Scanner/System.out
- [ ] Task 3: `EmployeeController.run()` menu loop + switch
- [ ] Task 4: `Main` wires view → controller → `run()`
- [ ] Task 5: Explain why the controller (not the view) decides flow

## Running

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="se.lexicon.Main"
```

See [Workshop_2_Employee_Management.md](Workshop_2_Employee_Management.md) for the full instructions, flowcharts and test scenarios.