package com.betroc.repository;

import com.betroc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail1);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}