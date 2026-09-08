package com.fleet.service;

import com.fleet.dto.LoginRequest;
import com.fleet.dto.LoginResponse;
import com.fleet.exception.InvalidCredentialsException;
import com.fleet.model.User;
import com.fleet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles the simple database-based login for this college project.
 * Passwords are stored in plain text in the "users" table on purpose,
 * to keep the authentication flow easy to explain in a viva. In a real
 * production system these would be hashed (e.g. with BCrypt).
 */
@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsernameIgnoreCase(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return new LoginResponse(true, "Login successful", user.getUsername(), user.getFullName(), user.getRole());
    }
}
