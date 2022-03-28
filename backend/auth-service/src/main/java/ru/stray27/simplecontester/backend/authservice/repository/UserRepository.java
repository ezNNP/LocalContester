package ru.stray27.simplecontester.backend.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stray27.simplecontester.backend.authservice.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
