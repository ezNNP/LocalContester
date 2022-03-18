package ru.stray27.simplecontester.backend.runner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stray27.simplecontester.backend.runner.model.RunInstance;

public interface RunInstanceRepository extends JpaRepository<RunInstance, Long> {
}
