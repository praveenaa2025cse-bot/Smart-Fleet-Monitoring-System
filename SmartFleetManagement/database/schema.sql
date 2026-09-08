-- ===================================================================
-- Smart Fleet Management System - Database Schema
-- ===================================================================

DROP DATABASE IF EXISTS smart_fleet;
CREATE DATABASE smart_fleet;
USE smart_fleet;

-- ---------------- users ----------------
CREATE TABLE users (
    user_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    full_name   VARCHAR(100),
    role        VARCHAR(30) DEFAULT 'ADMIN'
);

-- ---------------- vehicles ----------------
-- Single table used for Car / Truck / Van (JPA SINGLE_TABLE inheritance).
-- The "vehicle_category" column is the discriminator column.
CREATE TABLE vehicles (
    vehicle_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_category VARCHAR(20)  NOT NULL,           -- CAR / TRUCK / VAN
    vehicle_number   VARCHAR(20)  NOT NULL UNIQUE,
    model            VARCHAR(60)  NOT NULL,
    status           VARCHAR(20)  NOT NULL DEFAULT 'AVAILABLE'  -- AVAILABLE / ON_TRIP / UNDER_MAINTENANCE
);

-- ---------------- drivers ----------------
CREATE TABLE drivers (
    driver_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(100) NOT NULL,
    phone_number        VARCHAR(20)  NOT NULL,
    license_number      VARCHAR(40)  NOT NULL UNIQUE,
    assigned_vehicle_id BIGINT,
    CONSTRAINT fk_driver_vehicle FOREIGN KEY (assigned_vehicle_id) REFERENCES vehicles(vehicle_id)
        ON DELETE SET NULL
);

-- ---------------- trips ----------------
CREATE TABLE trips (
    trip_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id   BIGINT NOT NULL,
    driver_id    BIGINT NOT NULL,
    source       VARCHAR(100) NOT NULL,
    destination  VARCHAR(100) NOT NULL,
    distance     DOUBLE NOT NULL,
    trip_date    DATE NOT NULL,
    trip_status  VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- PENDING / ONGOING / COMPLETED
    CONSTRAINT fk_trip_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id) ON DELETE CASCADE,
    CONSTRAINT fk_trip_driver  FOREIGN KEY (driver_id)  REFERENCES drivers(driver_id)   ON DELETE CASCADE
);

-- ---------------- fuel_records ----------------
CREATE TABLE fuel_records (
    fuel_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id   BIGINT NOT NULL,
    fuel_type    VARCHAR(20) NOT NULL,   -- PETROL / DIESEL / CNG / ELECTRIC
    quantity     DOUBLE NOT NULL,
    cost         DOUBLE NOT NULL,
    record_date  DATE NOT NULL,
    CONSTRAINT fk_fuel_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id) ON DELETE CASCADE
);

-- ---------------- maintenance_records ----------------
CREATE TABLE maintenance_records (
    maintenance_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id     BIGINT NOT NULL,
    service_type   VARCHAR(100) NOT NULL,
    service_date   DATE NOT NULL,
    cost           DOUBLE NOT NULL,
    remarks        VARCHAR(255),
    next_due_date  DATE,
    CONSTRAINT fk_maintenance_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id) ON DELETE CASCADE
);

-- ===================================================================
-- Sample Data
-- ===================================================================

-- 1 admin user (username: admin / password: admin)
INSERT INTO users (username, password, full_name, role) VALUES
('admin', 'admin', 'Fleet Administrator', 'ADMIN');

-- 5 vehicles (mix of Car / Truck / Van)
INSERT INTO vehicles (vehicle_category, vehicle_number, model, status) VALUES
('CAR',   'TN01AB1234', 'Toyota Etios',        'AVAILABLE'),
('TRUCK', 'TN02CD5678', 'Tata 1613 LPT',       'ON_TRIP'),
('VAN',   'TN03EF9012', 'Maruti Eeco',         'AVAILABLE'),
('CAR',   'TN04GH3456', 'Honda City',          'UNDER_MAINTENANCE'),
('TRUCK', 'TN05IJ7890', 'Ashok Leyland Dost',  'AVAILABLE');

-- 5 drivers
INSERT INTO drivers (name, phone_number, license_number, assigned_vehicle_id) VALUES
('Ravi Kumar',    '9876543210', 'TN-DL-000111', 1),
('Suresh Babu',   '9876543211', 'TN-DL-000222', 2),
('Karthik Raja',  '9876543212', 'TN-DL-000333', NULL),
('Manoj Prasad',  '9876543213', 'TN-DL-000444', NULL),
('Arun Vignesh',  '9876543214', 'TN-DL-000555', 5);

-- 5 trips
INSERT INTO trips (vehicle_id, driver_id, source, destination, distance, trip_date, trip_status) VALUES
(1, 1, 'Chennai',      'Bangalore',  350.0, '2026-08-20', 'COMPLETED'),
(2, 2, 'Coimbatore',   'Madurai',    220.0, '2026-09-05', 'ONGOING'),
(3, 3, 'Salem',        'Erode',      65.0,  '2026-09-01', 'COMPLETED'),
(4, 4, 'Trichy',       'Thanjavur',  60.0,  '2026-08-15', 'COMPLETED'),
(5, 5, 'Chennai',      'Pondicherry',170.0, '2026-09-10', 'PENDING');

-- 5 fuel records
INSERT INTO fuel_records (vehicle_id, fuel_type, quantity, cost, record_date) VALUES
(1, 'PETROL', 35.0, 4200.00, '2026-08-19'),
(2, 'DIESEL', 80.0, 9200.00, '2026-09-04'),
(3, 'PETROL', 20.0, 2400.00, '2026-08-30'),
(4, 'PETROL', 30.0, 3600.00, '2026-08-14'),
(5, 'DIESEL', 60.0, 6900.00, '2026-09-09');

-- 5 maintenance records
-- NOTE: next_due_date values below are relative to a "today" of ~2026-09-08 so that,
-- out of the box, the dashboard's Smart Alerts show one OVERDUE example and one
-- UPCOMING (due within 7 days) example. Feel free to change the dates as time passes.
INSERT INTO maintenance_records (vehicle_id, service_type, service_date, cost, remarks, next_due_date) VALUES
(1, 'Oil Change',        '2026-07-15', 1500.00, 'Routine service',              '2026-10-15'),
(2, 'Brake Inspection',  '2026-08-01', 2500.00, 'Brake pads replaced',          '2026-09-05'),
(3, 'Tyre Replacement',  '2026-06-10', 8000.00, 'All 4 tyres replaced',         '2027-06-10'),
(4, 'Full Service',      '2026-09-06', 4500.00, 'Vehicle currently under maintenance', '2026-09-12'),
(5, 'AC Service',        '2026-08-25', 1200.00, 'Gas refill',                   '2026-12-25');
