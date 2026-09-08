package com.fleet.dto;

/** Outgoing payload after a successful login. */
public class LoginResponse {

    private boolean success;
    private String message;
    private String username;
    private String fullName;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(boolean success, String message, String username, String fullName, String role) {
        this.success = success;
        this.message = message;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
