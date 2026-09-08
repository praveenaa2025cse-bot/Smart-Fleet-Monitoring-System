package com.fleet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Smart Fleet Management System.
 *
 * Application flow:
 *   Browser -> HTML/CSS/JS -> Spring Boot REST Controllers -> Service Layer
 *            -> Repository Layer (Spring Data JPA) -> MySQL Database
 */
@SpringBootApplication
public class SmartFleetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartFleetApplication.class, args);
        System.out.println("==========================================================");
        System.out.println(" Smart Fleet Management System started successfully!");
        System.out.println(" Open the frontend/login.html file in your browser, or");
        System.out.println(" serve the 'frontend' folder and login with admin/admin");
        System.out.println(" Backend REST API base URL: http://localhost:8080/api");
        System.out.println("==========================================================");
    }
}
