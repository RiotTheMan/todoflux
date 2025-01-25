package com.fluxapp.todoflux.repository;

import com.fluxapp.todoflux.models.FluxUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<FluxUser, Long> {
    Optional<FluxUser> findByEmail(String email);  // Find a user by email
}
