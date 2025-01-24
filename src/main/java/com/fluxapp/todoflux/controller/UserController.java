package com.fluxapp.todoflux.controller;

import com.fluxapp.todoflux.entity.UserRegistrationRequest;
import com.fluxapp.todoflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserController {

    @Autowired
    private UserService userService; // Assume you have a UserService to handle business logic

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        try {
            // Validate the request (e.g., check if username and password are not empty)
            if (request.getUsername() == null || request.getUsername().isEmpty() ||
                    request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Username and password are required");
            }

            // Call the service to register the user
            long userId = userService.registerUser(request.getUsername(), request.getPassword(), request.getEmail());

            // Return the user ID with a 201 Created status
            return ResponseEntity.status(HttpStatus.CREATED).body(userId);
        } catch (Exception e) {
            // Handle exceptions (e.g., duplicate username, database errors)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }
}
