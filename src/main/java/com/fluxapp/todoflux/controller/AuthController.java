package com.fluxapp.todoflux.controller;

import com.fluxapp.todoflux.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService)   {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @GetMapping("/token")
    public String token(Authentication authentication) {
    log.debug("Token requested for user: '{}'", authentication.getName());
    String token = tokenService.generateToken(authentication);
    log.debug("Token granted {}", token);
    return token;
    };


    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestBody UserLoginRequest userLoginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
        return tokenService.generateToken(authentication);

        return null;
    }
//
//    @PostMapping("/token")
//    public ResponseEntity<String> token(@RequestHeader("Authorization") String authHeader) {
//            if (authHeader == null || !authHeader.startsWith("Basic ")) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or invalid Authorization header");
//            }
//
//            // Decode Base64 credentials
//            String base64Credentials = authHeader.substring("Basic ".length());
//            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
//            String[] values = credentials.split(":", 2);
//
//            if (values.length < 2) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization format");
//            }
//
//            String username = values[0];
//            String password = values[1];
//
//            UserLoginRequest userLoginRequest = new UserLoginRequest(username, password);
//        try {
//            String token = userService.authenticateUserAndGenerateToken(userLoginRequest);
//
//            return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }


}
