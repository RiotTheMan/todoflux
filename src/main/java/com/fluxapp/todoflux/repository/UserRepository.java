package com.fluxapp.todoflux.repository;

import com.fluxapp.todoflux.models.FluxUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<FluxUser, Long> {
    Optional<FluxUser> findByEmail(String email);
    Optional<FluxUser> findByUsernameOrEmail(String username, String email);

    FluxUser findByUsername(String username);
}
