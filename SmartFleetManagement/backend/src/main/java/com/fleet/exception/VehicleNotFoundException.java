package com.fleet.exception;

/** Thrown when a requested vehicle does not exist in the database. */
public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long id) {
        super("Vehicle not found with id: " + id);
    }
    public VehicleNotFoundException(String message) {
        super(message);
    }
}
