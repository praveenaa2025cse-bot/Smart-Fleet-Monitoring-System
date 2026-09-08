package com.fleet.exception;

/** Thrown when a requested trip does not exist in the database. */
public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(Long id) {
        super("Trip not found with id: " + id);
    }
    public TripNotFoundException(String message) {
        super(message);
    }
}
