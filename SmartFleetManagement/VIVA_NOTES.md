# VIVA NOTES — Smart Fleet Management System

Use this document to prepare for your college Java project viva/review.

---

## 1. What is the project?

The Smart Fleet Management System is a full-stack web application that helps a
company manage its vehicle fleet: vehicles, drivers, trips, fuel usage, and
maintenance. It provides a dashboard with live statistics and "smart alerts"
(e.g. maintenance due soon), plus reports on fuel/maintenance expenses and
overall vehicle operating cost.

## 2. Why was it developed?

Fleet-owning organizations (logistics, delivery, transport companies) need to
track vehicle availability, driver assignments, trip history, and running
costs (fuel + maintenance) in one place instead of spreadsheets. This project
demonstrates how a Java web application with a proper layered architecture and
a relational database can solve that problem.

## 3. How does the system work? (High-level flow)

```
User (browser)
   -> Frontend (HTML/CSS/JS, fetch() calls)
   -> REST API (Spring Boot @RestController)
   -> Controller (validates & delegates)
   -> Service (business logic, e.g. "trip Ongoing -> vehicle On Trip")
   -> Repository (Spring Data JPA)
   -> MySQL Database (smart_fleet)
```

Each module (Vehicle, Driver, Trip, Fuel, Maintenance) follows this same
Controller -> Service -> Repository -> Database flow.

## 4. Why Java?

Java is a strongly-typed, object-oriented, platform-independent language with
a mature ecosystem (Spring Boot, Hibernate/JPA) that is well suited to
building maintainable, layered enterprise web applications — and it's the
language taught in most college curricula, making it ideal for demonstrating
core OOP concepts.

## 5. Why Spring Boot?

Spring Boot removes most of the manual configuration needed for a Java web
app: it auto-configures an embedded web server (Tomcat), wires up REST
controllers, and integrates with Spring Data JPA for database access with
minimal boilerplate — letting the project focus on business logic and OOP
design rather than plumbing.

## 6. Why MySQL?

MySQL is a free, widely used relational database that models the project's
data (vehicles, drivers, trips, fuel, maintenance) naturally as related tables
with foreign keys, and integrates directly with Spring Data JPA/Hibernate.

---

## 7. Where is OOP used? (Detailed, with file references)

### 7.1 Classes and Objects
Every entity is a Java class: `User`, `Vehicle`, `Car`, `Truck`, `Van`,
`Driver`, `Trip`, `FuelRecord`, `MaintenanceRecord` (all in
`backend/src/main/java/com/fleet/model/`). Objects of these classes are
created throughout the service layer, e.g. `new Car(...)` in
`VehicleService.createVehicleInstance()`.

### 7.2 Encapsulation
Every entity keeps its fields `private` and exposes them only through public
getters and setters. Example from `Vehicle.java`:
```java
private String vehicleNumber;

public String getVehicleNumber() { return vehicleNumber; }
public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
```

### 7.3 Inheritance
`Vehicle` is the parent class. `Car`, `Truck`, and `Van` all extend it:
```java
public class Car extends Vehicle { ... }
public class Truck extends Vehicle { ... }
public class Van extends Vehicle { ... }
```
(See `model/Vehicle.java`, `model/Car.java`, `model/Truck.java`, `model/Van.java`.)
At the database level this is mapped using JPA's `SINGLE_TABLE` inheritance
strategy with a `vehicle_category` discriminator column, so all vehicle rows
live in one `vehicles` table but are loaded back as the correct Java subclass.

### 7.4 Abstraction
`Vehicle` is declared `abstract` and cannot be instantiated directly. It
declares two abstract methods that every subclass must implement:
```java
public abstract String getType();
public abstract double calculateMaintenanceFactor();
```
This forces each concrete vehicle type to define its own category name and
maintenance-cost multiplier while letting the rest of the application program
against the general `Vehicle` type.

### 7.5 Polymorphism
`VehicleService.createVehicleInstance()` demonstrates classic polymorphism:
```java
Vehicle vehicle;
switch (request.getType()) {
    case CAR:   vehicle = new Car(...);   break;
    case TRUCK: vehicle = new Truck(...); break;
    case VAN:   vehicle = new Van(...);   break;
}
```
The variable is declared as the abstract type `Vehicle`, but the actual object
can be a `Car`, `Truck`, or `Van`. When code elsewhere calls
`vehicle.getType()` or `vehicle.calculateMaintenanceFactor()`, Java calls the
overridden version specific to the real object at runtime (method overriding).
Every subclass overrides both methods with different behavior/values.

### 7.6 Constructors
Every model class has at least a no-argument constructor (required by JPA)
and a parameterized constructor for convenient object creation, e.g.
`Trip(Vehicle vehicle, Driver driver, String source, ...)` in `Trip.java`.

### 7.7 Methods
Meaningful methods exist across all layers:
- Model: `calculateMaintenanceFactor()`, `getType()`, `toString()`.
- Service: `createVehicle()`, `assignVehicle()`, `applyVehicleStatusForTrip()`,
  `getVehicleWiseFuelExpense()`, `getOverdueMaintenance()`, etc.
- Controller: one method per REST endpoint (`getAllVehicles`, `createTrip`, ...).

### 7.8 Exception Handling
Custom exceptions live in `backend/src/main/java/com/fleet/exception/`:
`VehicleNotFoundException`, `DriverNotFoundException`, `TripNotFoundException`,
`FuelRecordNotFoundException`, `MaintenanceRecordNotFoundException`,
`InvalidCredentialsException`, `InvalidRequestException`. They all extend
`RuntimeException`. A single `GlobalExceptionHandler`
(`@RestControllerAdvice`) catches them and converts each into the correct
HTTP status code (404 Not Found, 400 Bad Request, 401 Unauthorized, etc.)
with a JSON error body, so the frontend always gets a clear, structured error.

### 7.9 Collections
`List`, `ArrayList`, `Map`, and `HashMap` are used throughout the service
layer, for example:
- `List<Vehicle>` returned by `VehicleService.getAllVehicles()`.
- `Map<String, Double>` built in `FuelService.getVehicleWiseFuelExpense()` to
  aggregate fuel cost per vehicle number.
- `List<MaintenanceRecord>` built in `MaintenanceService.getOverdueMaintenance()`.

---

## 8. What are REST APIs?

REST (Representational State Transfer) is an architectural style for web
services where each resource (vehicle, driver, trip, ...) is exposed at a URL
and manipulated using standard HTTP methods:
- `GET` — read data
- `POST` — create data
- `PUT` — update data
- `DELETE` — remove data

This project exposes REST endpoints such as `GET /api/vehicles`,
`POST /api/vehicles`, `PUT /api/vehicles/{id}`, and `DELETE /api/vehicles/{id}`
for every module, returning/accepting JSON.

## 9. How does the frontend communicate with the backend?

The frontend is plain HTML/CSS/JavaScript. Every page uses the browser's
`fetch()` API (wrapped in a small helper, `api.get/post/put/del`, in
`frontend/js/app.js`) to call the Spring Boot REST endpoints, send/receive
JSON, and update the DOM with the results — no page reloads are needed for
CRUD operations.

## 10. How is data stored in MySQL?

Spring Data JPA/Hibernate maps each `@Entity` class to a MySQL table
(`vehicles`, `drivers`, `trips`, `fuel_records`, `maintenance_records`,
`users`) as defined in `database/schema.sql`. Relationships between tables
(e.g. a `Trip` referencing a `Vehicle` and a `Driver`) are modeled with
`@ManyToOne` + `@JoinColumn`, which Hibernate turns into SQL foreign keys and
JOIN queries automatically.

## 11. Smart features / business rules worth mentioning in viva

- **Trip status drives vehicle status**: marking a trip "Ongoing" sets its
  vehicle to "On Trip"; marking it "Completed" sets the vehicle back to
  "Available" (see `TripService.applyVehicleStatusForTrip()`).
- **Maintenance due alerts**: each maintenance record can have a
  `nextDueDate`; the dashboard highlights vehicles that are overdue or due
  within 7 days (see `MaintenanceService.getOverdueMaintenance()` /
  `getUpcomingMaintenance()`).
- **Operating cost**: computed per vehicle as
  `fuelExpense + maintenanceExpense` in `ReportService.getOperatingCostReport()`
  and `VehicleSummaryResponse`.

## 12. Sample Q&A

**Q: Why is Vehicle abstract instead of a normal class?**
A: Because a "generic vehicle" with no concrete category doesn't make sense
in this domain — every real vehicle is specifically a Car, Truck, or Van.
Making `Vehicle` abstract enforces that only meaningful subclasses can be
created, which is a textbook use of abstraction.

**Q: What is the benefit of using DTOs instead of exposing entities directly
in requests?**
A: DTOs (`VehicleRequest`, `TripRequest`, etc.) decouple the API's input
shape from the database entity's shape, let us apply validation annotations
(`@NotBlank`, `@NotNull`) independently, and avoid exposing internal JPA
relationship fields that clients shouldn't set directly.

**Q: What happens if you try to create a trip for a vehicle under
maintenance?**
A: `TripService.createTrip()` throws a custom `InvalidRequestException`,
which the `GlobalExceptionHandler` converts into an HTTP 400 response with a
clear message, and the frontend shows this to the user via an alert.
