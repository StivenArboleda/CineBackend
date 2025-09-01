package com.fabrica.cine.backend.repository;

import com.fabrica.cine.backend.model.Movie;
import com.fabrica.cine.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByPhoneContainingIgnoreCase(String phone);
    List<User> findByEmailIgnoreCase(String email);
    List<User> findByEmailContainingIgnoreCaseAndPhoneIgnoreCase(String email, String phone);
}
