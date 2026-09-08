package com.fleet.exception;

/** Thrown when a requested fuel record does not exist in the database. */
public class FuelRecordNotFoundException extends RuntimeException {
    public FuelRecordNotFoundException(Long id) {
        super("Fuel record not found with id: " + id);
    }
}
