package com.fleet.exception;

/** Thrown when a requested maintenance record does not exist in the database. */
public class MaintenanceRecordNotFoundException extends RuntimeException {
    public MaintenanceRecordNotFoundException(Long id) {
        super("Maintenance record not found with id: " + id);
    }
}
