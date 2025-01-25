package com.fluxapp.todoflux.service;

import com.fluxapp.todoflux.models.FluxUser;
import com.fluxapp.todoflux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean existsUserByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public long registerUser(String username, String password, String email) throws Exception {
        // Check if the user already exists by email
        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("Email is already taken");
        }

        // Create and save the new user if email is not already taken
        FluxUser newUser = new FluxUser();
        newUser.setUsername(username);
        newUser.setPassword(password);  // You should hash the password in a real app
        newUser.setEmail(email);

        FluxUser savedUser = userRepository.save(newUser);

        return savedUser.getId();  // Return the user ID after saving
    }

}
