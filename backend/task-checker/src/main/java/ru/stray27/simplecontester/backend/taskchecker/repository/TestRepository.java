package ru.stray27.simplecontester.backend.taskchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stray27.simplecontester.backend.taskchecker.model.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
}
