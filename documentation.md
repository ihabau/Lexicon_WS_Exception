![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Documentation — Workshop Series (10 steps to an Event-Manager-style app)

This file is your **central reference** for the new material introduced across the `workshop-*`
branches. Each workshop branch has its own `Workshop_N_*.md` worksheet with the hands-on tasks;
this file explains the **concepts** (SQL, service, exceptions, util) and how the layers fit
together, so you can look things up quickly while you work.

## The architecture you build up, step by step

| Step | Branch | Layer / concept introduced |
|:---:|---|:---:|
| 1 | workshop-1-library-books | `model` — encapsulation + validation (unchecked exceptions, regex) |
| 2 | workshop-2-employee-mgmt | `ui` + `controller` — the MVC split |
| 3 | workshop-3-product-inventory | `dao` interface + file-based `daoImpl` |
| 4 | workshop-4-student-grades | `exception` — custom exceptions + centralized `ExceptionHandler` |
| 5 | workshop-5-task-manager | enums, two related entities, streams |
| 6 | workshop-6-recipe-manager | `service` layer — business rules |
| 7 | workshop-7-vehicle-registration | `util` + SQL schema + first JDBC DAO |
| 8 | workshop-8-movie-collection | full JDBC `daoImpl` CRUD |
| 9 | workshop-9-bank-account | relations + transactions + base exception |
| 10 | workshop-10-pet-adoption | everything combined (capstone) |

The general request-flow through a fully built app:

```
view  --user action-->  controller  --pure business rules-->  service  --persistence-->  dao  -->  (file | MySQL)
   ^                         |                                                                   |
   +------------------------------------- exceptions ---------------------------------------+
```

---

## 1. The model layer (Step 1)

*   Fields are `private`; the outside world uses **getters** — that is *encapsulation*.
*   Validate inside the constructor/setters with **unchecked exceptions**
    (`IllegalArgumentException`, later custom exceptions) because a bad argument is a
    *programmer/input* error, not something the caller is expected to recover from.
*   Use **regex** for format checks, e.g. ISBN `^\d{13}$`, plate `^[A-Z]{3}\d{3}$`.
*   Provide a readable `toString()` and `equals`/`hashCode` when you compare objects by value.

## 2. UI + controller — the MVC split (Step 2)

*   `View` = I/O only (read input, print results; never business logic).
*   `Controller` = orchestrator: read input → call the service/dao → show the result via the view.
*   Keep the controller thin — if it starts validating rules, move them to the service.

## 3. DAO + DAOImpl (Step 3)

*   `*DAO` is an **interface** = the contract (what the rest of the app depends on).
*   `*DAOImpl` (e.g. `FileXxxDAOImpl`) is the implementation = the concrete persistence.
*   The rest of the app depends on the *interface*, so you can swap file storage for a database
    (Step 7) without touching the controller/service.
*   Low-level failures (`IOException`, `SQLException`) are **wrapped** in your own exceptions.

## 4. Exceptions (Step 4) — a big focus of this series

### Checked vs unchecked

| | Checked (`extends Exception`) | Unchecked (`extends RuntimeException`) |
|---|---|---|
| Compiler | forces you to handle it | no compile-time obligation |
| Best for | recoverable, *expected* failures (file missing, duplicate id) | programmer errors + validation of one object (bad argument) |
| Example | `StudentStorageException` | `InvalidGradeException`, `IllegalArgumentException` |

### A custom exception family

1.  A **base** exception (`StudentException`, `EventException`) with a `(String message)` constructor.
2.  **Subclasses** for each failure type (`InvalidGradeException`, `DuplicateStudentException`, `StudentStorageException`).
3.  Use `super(message)` (and `super(message, cause)` to preserve the original stack trace).

### Where each role happens

*   **Model** — throws specific descriptive exceptions (validation).
*   **DAO** — catches low-level (`IOException`/`SQLException`) and *wraps* them in your exception.
*   **Controller/UI** — catches app exceptions **once, centrally** via `ExceptionHandler.handle(...)`
    and prints a friendly message (no repeated try/catch nests).

### Centralized ExceptionHandler

```java
public final class ExceptionHandler {
    public static void handle(Exception e, View view) {
        if (e instanceof YourEventException) {
            view.displayError(e.getMessage());
        } else if (e instanceof NumberFormatException) {
            view.displayError("Please enter a valid number.");
        } else {
            view.displayError("Unexpected error: " + e.getMessage());
            e.printStackTrace(); // keep for debugging only
        }
    }
}
```

Catch the **most specific type first** as you walk down with `instanceof`.

## 5. Enums, entities, streams (Step 5)

*   **Enum** — a fixed set of values (e.g. `TaskStatus { TODO, IN_PROGRESS, DONE }`).
*   **Multiple related entities** — one model referencing another (+ `List<...>` fields).
*   **Streams** — filter/map/sort collections and turn the result back with `collect(...)`.

## 6. The service layer (Step 6)

*   The `Service` receives a `DAO` (interface) through its **constructor** and hides it from the UI.
*   **Business rules live in the service**, not the controller:
    *   "ingredient exists"
    *   "no duplicate name"
    *   "cannot edit a published item"
*   The controller becomes a thin orchestrator — it has **no reference to the DAO**.
*   Single responsibility recap: **UI = I/O, Service = rules, DAO = persistence**.

## 7. Util + SQL + JDBC (Step 7)

### The util layer
A `util` package holds reusable, stateless helpers. The key one is `DatabaseConnection` — a
**singleton** that hands out JDBC `Connection`s from one place:

```java
public final class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/my_db";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password_here";

    private DatabaseConnection() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

*Keep real passwords out of committed code — use env vars or a properties file.*

### SQL schema
A `.sql` file builds the table once (run in MySQL Workbench):

```sql
CREATE DATABASE IF NOT EXISTS my_db;
USE my_db;

CREATE TABLE IF NOT EXISTS vehicle (
    id INT AUTO_INCREMENT PRIMARY KEY,
    registration_number VARCHAR(10) NOT NULL UNIQUE,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    model_year INT NOT NULL,
    owner_name VARCHAR(100) NOT NULL
);
```

Note `UNIQUE` — a database-level guard that mirrors your service-level duplicate check.

### The JDBC dance

```java
// READ
String sql = "SELECT * FROM vehicle";
try (Connection c = DatabaseConnection.getConnection();
     Statement st = c.createStatement();
     ResultSet rs = st.executeQuery(sql)) {
    while (rs.next()) {
        vehicles.add(new Vehicle(
            rs.getString("registration_number"),
            rs.getString("brand"),
            rs.getString("model"),
            rs.getInt("model_year"),
            rs.getString("owner_name")));
    }
} catch (SQLException e) {
    throw new VehicleStorageException("Could not read vehicles", e);
}

// WRITE — always a PreparedStatement with ? placeholders
String sql = "INSERT INTO vehicle (registration_number, brand, model, model_year, owner_name) VALUES (?, ?, ?, ?, ?)";
try (Connection c = DatabaseConnection.getConnection();
     PreparedStatement ps = c.prepareStatement(sql)) {
    ps.setString(1, v.getRegistrationNumber());
    ps.setString(2, v.getBrand());
    ps.setString(3, v.getModel());
    ps.setInt(4, v.getModelYear());
    ps.setString(5, v.getOwnerName());
    ps.executeUpdate();
} catch (SQLException e) {
    throw new VehicleStorageException("Could not save vehicle", e);
}
```

Key rules:
*   **One connection per operation**, closed with `try-with-resources` (never a shared long-lived one).
*   **`PreparedStatement` with `?`** prevents SQL injection — never concatenate user input into SQL.
*   **Resultsets are 1-based**, but prefer *named* columns (`rs.getString("brand")`).
*   Map `ResultSet` → object yourself; wrap every `SQLException` in your own storage exception.

## 8. Full JDBC CRUD (Step 8)

Add the remaining CRUD operations with `PreparedStatement`:

*   `save` → `INSERT`
*   `findById`/`findAll` → `SELECT ... WHERE id = ?`
*   `update` → `UPDATE table SET col = ? WHERE id = ?`
*   `delete` → `DELETE FROM table WHERE id = ?`
*   **Generated keys**: after an insert you may need the new auto-increment id —
    pass `Statement.RETURN_GENERATED_KEYS` to `prepareStatement` and read `ps.getGeneratedKeys()`.

## 9. Relations + transactions + base exception (Step 9)

*   **Relations** — a table references another via a foreign key (`FOREIGN KEY (x) REFERENCES y(id)`);
    a `JOIN` or two queries/objects model the "has-a" relationship in the DAO.
*   **Transactions** — group several statements so they succeed or fail *together*:
    `con.setAutoCommit(false)` → run statements → `con.commit()` (or `con.rollback()` on failure),
    always restoring `setAutoCommit(true)`.
*   **Base exception** — one shared base type (e.g. `BankAccountException`) so the handler
    can catch the whole family once.

## 10. Capstone (Step 10)

Combine every layer from steps 1–9 into one complete Event-Manager-style application:
model → dao → daoImpl(JDCB) → service → ui → controller, with a full exception family, a
`DatabaseConnection` util, a SQL schema, and transactions/relations where they belong.
Fewest hints — this is where you prove you know the whole architecture.

---

## Quick troubleshooting

*   `ClassNotFoundException: com.mysql...` → the `mysql-connector-j` dependency is missing from `pom.xml` (run `mvn compile` first — it downloads).
*   `Unknown database 'my_db'` → you forgot to run the `.sql` schema once in Workbench.
*   `SQLException` leaking to the UI → wrap it: DAO catches `SQLException`, throws your storage exception, handler prints the message.
*   Duplicate data sneaking in → add `UNIQUE` in the schema *and* a duplicate check in the service.
*   App hangs → you're holding a single long-lived `Connection`; open one per operation and close it.

---

> Learn-by-doing note: each branch is deliberately **scaffolded** (only a `Main.java` stub plus the
> worksheet). Implement the worksheet, commit after each task, and push the branch when the
> checklist is done. Don't copy the Event Manager code — write your own to make it stick.
