![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Pet Adoption System — the full clone

> **Step 10 of 10** — the capstone. You reassemble every pattern from steps 1–9 into one application
> that is architecturally identical to the **Event Manager** reference app (`event_management_app`):
> model + validation, enums, streams, DAO (+ file and JDBC impls), service, presenter/view, controller,
> utility, SQL schema, and a unified exception family.

| Difficulty | Hints provided | The whole architecture |
|:---:|:---:|:---:|
| 10 / 10 | 2 | **Everything** — a full clone, only from the diagrams below |

---

## Objective

No walkthrough this time. Nine worksheets gave you each layer; now the **entire** Event-Manager
architecture has to appear in one Pet Adoption system. You get a complete class diagram, a feature
list and two hints. How you allocate tasks, in what order, and how you test it is your call —
exactly how the reference app was built.

**Reference:** `C:\...\Lexicon_WS_event_management_app` is open on your machine. Mirroring its
`se.lexicon.*` structure is not cheating — it is the exercise.

## Learning Goals

*   Assemble a full layered application from a blank `pom.xml`.
*   Design one schema that supports the features (FK, enum-backed column).
*   Ship both a file DAO and a JDBC DAO for the same interface, swapped in `Main`.
*   Explain how each step 1–9 pattern shows up in the final product.

---

## Prerequisites

*   MySQL running; **you** create the database and schema (`SQL_database/pet_adoption.sql` — your file to design).
*   All steps 1–9 understood — this is the exam, not the lesson.
*   Commit per feature; push when done.

---

## Feature list (implement ALL)

1.  **Register a pet** — name, species, age. Validation: blank name/species, age < 0 → error.
2.  **List pets** — all; only available; only adopted (step-5 streams).
3.  **Adopt a pet** — by id, to a named owner. Already-adopted → `PetAlreadyAdoptedException`.
4.  **List adopted pets per owner** — JOIN (step 9).
5.  **Unblock via 9 menu options** — add, list all, list available, list adopted, adopt, my adoptions, update pet, delete pet, exit.
6.  **Duplicates** — same name+species pet registered twice → `DuplicatePetException`.
7.  **Storage both ways** — a `FilePetDAOImpl` *and* a `PetJdbcDAOImpl`; `Main` decides which one runs.
8.  **No stack traces** — every error goes through `ExceptionHandler`. Only you and the instructor may read `e.getMessage()`.

---

## The complete class diagram

```mermaid
classDiagram
    namespace model {
        class Pet {
            -int id
            -String name
            -String species
            -int age
            -PetStatus status
            +Pet(id, name, species, age[, status])
        }
        class PetStatus {
            <<enum>>
            AVAILABLE
            PENDING
            ADOPTED
        }
        class PetOwner {
            -int id
            -String name
            -String email
        }
        class Adoption {
            -int id
            -Pet pet
            -PetOwner owner
            -LocalDate date
        }
        Pet --> PetStatus
        Adoption "1" --> "1" Pet
        Adoption "N" --> "1" PetOwner
    }

    namespace exception {
        class PetException {
            <<abstract>>
        }
        class PetStorageException
        class DuplicatePetException
        class PetAlreadyAdoptedException
        class PetNotFoundException
        class ExceptionHandler {
            +handle(Exception e)$ void
        }
        PetException <|-- PetStorageException
        PetException <|-- DuplicatePetException
        PetException <|-- PetAlreadyAdoptedException
        PetException <|-- PetNotFoundException
    }

    namespace data {
        class PetDAO {
            <<interface>>
            +findAll() List~Pet~
            +findByStatus(PetStatus) List~Pet~
            +findByOwner(int ownerId) List~Adoption~
            +save(Pet) Pet
            +adopt(Adoption) void
            +update(Pet) void
            +deleteById(int) boolean
            +count() long
        }
        class FilePetDAOImpl {
            -Path filePath
        }
        class PetJdbcDAOImpl {
            -ResultSet + PreparedStatement
        }
        PetDAO <|.. FilePetDAOImpl
        PetDAO <|.. PetJdbcDAOImpl
    }

    namespace service {
        class PetService {
            -PetDAO petDAO
            +registerPet(...) Pet
            +listAvailable() List~Pet~
            +adopt(petId, owner) Adoption
            +adoptionsByOwner(ownerId) List~Adoption~
        }
        class PetOwnerService {
            -PetDAO petDAO
            +createOwner(name, email) PetOwner
        }
    }

    namespace util {
        class DatabaseConnection {
            +getConnection() Connection$
        }
    }

    namespace ui {
        class PetView
        class PetController {
            -PetService petService
            -PetOwnerService ownerService
            -PetView view
            +run() void
        }
    }

    PetJdbcDAOImpl --> DatabaseConnection
    PetController --> PetService
    PetController --> PetOwnerService
    PetController --> PetView
    PetController ..> ExceptionHandler
```

---

## Layer-by-layer recaps (what each step taught the layer)

| Step | Pattern | Where it lives in the capstone |
|:---:|---|:---:|
| 1 | model + validation | `Pet` constructor/setters validate; `PetStatus` enum replaces `boolean adopted` |
| 2 | view + controller (MVC) | `PetView` + `PetController`, menu loop, view-only I/O |
| 3 | dao + daoImpl (file) | `PetDAO` + `FilePetDAOImpl` |
| 4 | exception family + handler | 5-class hierarchy + `ExceptionHandler.handle` |
| 5 | enum + streams + composition | `PetStatus`, `Adoption` (pet + owner), `listAvailable()`, per-owner queries |
| 6 | service layer | `PetService` + `PetOwnerService` own every rule; controller calls only services |
| 7 | util + SQL + first JDBC DAO | `DatabaseConnection`, `pet_adoption.sql` |
| 8 | full JDBC CRUD | `PetJdbcDAOImpl` — generated keys, update, delete, count |
| 9 | relations + transactions | `adopt(...)` is a **transaction**: `AVAILABLE→ADOPTED` + one `Adoption` row, commit/rollback |

---

## Your schema — requirements, not a script

`pet_adoption.sql` must satisfy the DAO queries. You decide table names, types and the enum storage
(think hard about what the *service's* streams need). Minimum surfaces:

*   `pet` (`id`, `name`, `species`, `age`, `status`).
*   `pet_owner` (`id`, `name`, `email` — email unique).
*   `adoption` (`id`, `pet_id` FK, `owner_id` FK, `adoption_date`) — the JOIN join-table of step 9.
*   An **atomic** `adopt` — either both the status flip and the `adoption` row happen, or neither.

---

## Suggested order (yours may differ — that's the point)

1.  `pom.xml` (mysql-connector) + packages + `DatabaseConnection`.
2.  `PetStatus`, `Pet` (+ validation), `PetOwner`, `Adoption`.
3.  Exception hierarchy + `ExceptionHandler`.
4.  Schema → tables in Workbench.
5.  `PetDAO` interface → `FilePetDAOImpl` → `PetJdbcDAOImpl` (run the file DAO until features 1–2 pass).
6.  `PetService` + `PetOwnerService` (rules + streams).
7.  `PetView` + `PetController` + `Main`; flip `Main` to the JDBC DAO.
8.  The transaction in `PetJdbcDAOImpl.adopt(...)`.
9.  Test every scenario below with **both** DAOs.

---

## Test scenarios

| # | With file DAO | With JDBC DAO | Both must |
|---|--------------:|--------------:|-----------|
| 1 | register `Whiskers` (cat, 2) | same | appear in "list all" |
| 2 | register `Whiskers` cat 2 again | same | reject as duplicate |
| 3 | adopt `Whiskers` (owner "Anna") | same | flip to ADOPTED, create Adoption |
| 4 | adopt `Whiskers` again | same | reject — **no** second Adoption row, status unchanged (rollback!) |
| 5 | "my adoptions" for Anna | same | show Whiskers via JOIN |
| 6 | delete a pet, re-list | same | gone; `count()` drops |
| 7 | restart app | restart app | state persisted, nothing invented |

---

## Hints & Help (2 hints)

1. **The transaction is the trap.** In `adopt(...)` use ONE connection, `setAutoCommit(false)`,
   flip the status with one `UPDATE` and insert the `Adoption` with a second statement, then `commit`.
   Rollback on any failure — and re-check with test 4 by adopting the same pet twice. A split
   transaction is the only way test 4 can pass *wrongly*.
2. Because your services are already peeled into `PetService` + `PetOwnerService`, `Main` is small:

```java
PetDAO dao = useFile ? new FilePetDAOImpl() : new PetJdbcDAOImpl();
PetService    pets   = new PetService(dao);
PetOwnerService owners = new PetOwnerService(dao);
new PetController(pets, owners, new PetView()).run();
```

If `Main` grows beyond ~15 lines, the layering isn't holding — go back and reread step 6.

---

## Checklist

- [ ] `Pet`, `PetStatus`, `PetOwner`, `Adoption` with validation (step 1 + 5)
- [ ] Exception family from an abstract `PetException` + `ExceptionHandler` (step 4)
- [ ] `PetDAO` interface with the full feature set (step 3/8 surface)
- [ ] `FilePetDAOImpl` **and** `PetJdbcDAOImpl` — swapped in `Main` (steps 3, 7, 8)
- [ ] Services own every rule; controller has no SQL (step 6)
- [ ] `pet_adoption.sql`: 3 tables, FKs, unique owner email (step 7)
- [ ] `adopt` is one transaction — test 4 proves rollback (step 9)
- [ ] All queries used by streams (`listAvailable`) match the schema (step 5)
- [ ] Every error → handler → friendly message (steps 2 + 4)
- [ ] All 7 scenarios pass with both DAOs, including restart persistence

## Bonus Challenge (optional)

Statistics screen: top adopter, most popular species, average pet age at adoption — five one-line
streams powered by your `findAll`/`findByOwner` data. Tell the instructor which Event-Manager method
you stole the idea from.

**You are done. The Event Manager app is now yours.**