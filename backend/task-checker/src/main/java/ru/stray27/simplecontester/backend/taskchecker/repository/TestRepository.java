package ru.stray27.simplecontester.backend.taskchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stray27.simplecontester.backend.taskchecker.model.Test;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByUsername(String username);
}
