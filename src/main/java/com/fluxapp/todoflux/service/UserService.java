package com.fluxapp.todoflux.service;

import com.fluxapp.todoflux.models.FluxUser;
import com.fluxapp.todoflux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsUserByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public long registerUser(String username, String password, String email) throws Exception {
        // Check if the user already exists by email
        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("Email is already taken");
        }
        // TODO : unify these checks
        if (userRepository.findByUsernameOrEmail(username, email).isPresent()) {
            throw new Exception("Username is already taken");
        }

        // Create and save the new user if email is not already taken
        FluxUser newUser = new FluxUser();
        newUser.setUsername(username);
        newUser.setPassword("{bcrypt}" + passwordEncoder.encode(password));
        newUser.setEmail(email);

        FluxUser savedUser = userRepository.save(newUser);

        return savedUser.getId();
    }

    public FluxUser getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName());
    }

}
