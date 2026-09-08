package com.fleet.exception;

/** Thrown when login username/password do not match any user. */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
