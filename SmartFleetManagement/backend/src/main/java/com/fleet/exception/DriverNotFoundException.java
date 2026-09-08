package com.fleet.exception;

/** Thrown when a requested driver does not exist in the database. */
public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(Long id) {
        super("Driver not found with id: " + id);
    }
    public DriverNotFoundException(String message) {
        super(message);
    }
}
