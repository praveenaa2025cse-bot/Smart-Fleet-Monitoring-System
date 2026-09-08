# Smart Fleet Management System

A complete, full-stack **Java Spring Boot + MySQL + HTML/CSS/JavaScript** web application
for managing a company's vehicle fleet: vehicles, drivers, trips, fuel, and maintenance,
with a dashboard, smart alerts, and reports.

```
Browser (HTML/CSS/JS)
     |  fetch() / AJAX
     v
Spring Boot REST Controllers
     v
Service Layer (business logic)
     v
Repository Layer (Spring Data JPA)
     v
MySQL Database (smart_fleet)
```

---

## 1. Technology Stack

| Layer        | Technology                          |
|--------------|--------------------------------------|
| Backend      | Java 17, Spring Boot 3.2             |
| Frontend     | HTML5, CSS3, Vanilla JavaScript (fetch API) |
| Database     | MySQL 8                              |
| ORM          | Spring Data JPA / Hibernate          |
| Build Tool   | Maven                                |

---

## 2. Project Structure

```
SmartFleetManagement/
├── backend/                     # Spring Boot application
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/fleet/
│       │   ├── controller/      # REST controllers
│       │   ├── service/         # Business logic
│       │   ├── repository/      # Spring Data JPA repositories
│       │   ├── model/           # JPA entities (Vehicle, Car, Truck, Van, ...)
│       │   │   └── enums/
│       │   ├── dto/             # Request/response DTOs
│       │   ├── exception/       # Custom exceptions + global handler
│       │   ├── config/          # CORS configuration
│       │   └── SmartFleetApplication.java
│       └── resources/
│           └── application.properties
├── frontend/                    # Static HTML/CSS/JS app
│   ├── login.html
│   ├── dashboard.html
│   ├── vehicles.html
│   ├── drivers.html
│   ├── trips.html
│   ├── fuel.html
│   ├── maintenance.html
│   ├── reports.html
│   ├── css/style.css
│   └── js/app.js
├── database/
│   └── schema.sql               # CREATE TABLE + sample data
└── README.md
```

---

## 3. Prerequisites

Install the following before running the project:

1. **Java 17** (JDK) — https://adoptium.net
2. **Maven 3.8+** — https://maven.apache.org
3. **MySQL 8** (Server + a client such as MySQL Workbench or the `mysql` CLI)

Verify installations:

```bash
java -version
mvn -version
mysql --version
```

---

## 4. Database Setup

1. Start your MySQL server.
2. Run the provided schema (creates the `smart_fleet` database, tables, and sample data):

```bash
mysql -u root -p < database/schema.sql
```

This creates:
- 1 admin user (`admin` / `admin`)
- 5 vehicles (mix of Car/Truck/Van)
- 5 drivers
- 5 trips
- 5 fuel records
- 5 maintenance records

---

## 5. Configure the Backend

Open `backend/src/main/resources/application.properties` and update your MySQL
username/password if they differ from the defaults:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_fleet?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
```

---

## 6. Run the Spring Boot Backend

From the `backend` folder:

```bash
cd backend
mvn spring-boot:run
```

The backend will start at:

```
http://localhost:8080
```

You should see REST endpoints available under `http://localhost:8080/api/...`
(e.g. `http://localhost:8080/api/vehicles`).

> Alternative: build a runnable jar and run it directly:
> ```bash
> mvn clean package
> java -jar target/smart-fleet-management.jar
> ```

---

## 7. Open the Frontend

The frontend is a set of static HTML files that call the backend via `fetch()`.
You can open it in either of these ways:

**Option A — Directly in the browser**
Simply double-click `frontend/login.html` (or open it via `File > Open` in your browser).

**Option B — Serve it with a simple local web server (recommended)**
From the `frontend` folder:

```bash
cd frontend
python3 -m http.server 5500
```

Then visit: `http://localhost:5500/login.html`

> If your backend is not on `http://localhost:8080`, edit the `API_BASE` constant
> at the top of `frontend/js/app.js`.

---

## 8. Login

Use the sample credentials (also shown on the login page):

```
Username: admin
Password: admin
```

---

## 9. Test Each Module

1. **Dashboard** — view fleet-wide stats and smart maintenance/availability alerts.
2. **Vehicles** — add a vehicle (choose Car/Truck/Van), search, edit, delete.
3. **Drivers** — add a driver, assign/unassign them to an available vehicle.
4. **Trips** — create a trip; set status to "Ongoing" (vehicle becomes On Trip)
   then "Completed" (vehicle becomes Available again).
5. **Fuel** — log fuel purchases per vehicle; see the running total fuel expense.
6. **Maintenance** — log a service record with a "next due date"; watch the
   Smart Alerts show up when a due date is close or overdue. Use "Mark Available"
   to bring a serviced vehicle back into rotation.
7. **Reports** — view vehicle-wise fuel/maintenance expenses, vehicle status
   summary, and the full operating-cost performance table.

---

## 10. REST API Reference

| Module      | Endpoints |
|-------------|-----------|
| Auth        | `POST /api/login` |
| Vehicles    | `GET/POST /api/vehicles`, `GET/PUT/DELETE /api/vehicles/{id}` |
| Drivers     | `GET/POST /api/drivers`, `GET/PUT/DELETE /api/drivers/{id}`, `PUT /api/drivers/{id}/assign-vehicle` |
| Trips       | `GET/POST /api/trips`, `GET/PUT/DELETE /api/trips/{id}` |
| Fuel        | `GET/POST /api/fuel`, `GET/PUT/DELETE /api/fuel/{id}`, `GET /api/fuel/total-expense` |
| Maintenance | `GET/POST /api/maintenance`, `GET/PUT/DELETE /api/maintenance/{id}`, `GET /api/maintenance/due`, `GET /api/maintenance/upcoming`, `PUT /api/maintenance/vehicle/{id}/complete` |
| Dashboard   | `GET /api/dashboard` |
| Reports     | `GET /api/reports/fuel`, `GET /api/reports/maintenance`, `GET /api/reports/operating-cost`, `GET /api/reports/vehicle-summary`, `GET /api/reports/vehicle-status-summary` |

---

## 11. Troubleshooting

- **`Communications link failure` / cannot connect to MySQL** — make sure MySQL
  is running and the credentials in `application.properties` are correct.
- **CORS errors in the browser console** — the backend already allows all
  origins for `/api/**` (see `config/CorsConfig.java`); make sure you're calling
  the correct `API_BASE` URL in `frontend/js/app.js`.
- **Port 8080 already in use** — change `server.port` in `application.properties`
  and update `API_BASE` in `frontend/js/app.js` to match.
- **Table already exists / data conflicts** — re-run `database/schema.sql`; it
  starts with `DROP DATABASE IF EXISTS smart_fleet` so it always gives you a
  clean slate.

---

See **VIVA_NOTES.md** for a full explanation of the project's design and every
OOP concept used, ready for a college viva/review.
