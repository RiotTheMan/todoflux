package com.fluxapp.todoflux.controller;

import com.fluxapp.todoflux.models.LoginRequest;
import com.fluxapp.todoflux.service.TokenService;
import com.fluxapp.todoflux.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(TokenService tokenService, UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/token")
    public String token(@RequestBody LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
        return tokenService.generateToken(authentication);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest request) {
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
