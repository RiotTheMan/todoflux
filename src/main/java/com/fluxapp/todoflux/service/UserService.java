package com.fluxapp.todoflux.service;

import com.fluxapp.todoflux.entity.FluxUser;
import com.fluxapp.todoflux.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("user-service")
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public long registerUser(String username, String password, String email) {
        // Check if the username already exists
        if (userRepository.existsUserByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsUserByUsername(username)) {
            throw new RuntimeException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(password);

        // Save the user to the database
        FluxUser fluxUser = new FluxUser();
        fluxUser.setUsername(username);
        fluxUser.setEmail(email);
        fluxUser.setPassword(hashedPassword);
        userRepository.save(fluxUser);

        // Return the user ID
        return fluxUser.getId();
    }
}
