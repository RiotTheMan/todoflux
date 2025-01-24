package com.fluxapp.todoflux.repository;

import com.fluxapp.todoflux.entity.FluxUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<FluxUser, Long> {

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

//    @Transactional
//    public Boolean existsUserByEmail(String username) {
//        String query = "SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username";
//        return entityManager.createQuery(query, Boolean.class)
//                .setParameter("username", username)
//                .getSingleResult();
//    }

}
