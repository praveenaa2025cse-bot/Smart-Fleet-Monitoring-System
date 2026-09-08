package com.fleet.exception;

/** Thrown for generic bad-input situations, e.g. assigning an unavailable vehicle. */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
