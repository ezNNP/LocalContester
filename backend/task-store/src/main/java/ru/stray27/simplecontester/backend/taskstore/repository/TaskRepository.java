package ru.stray27.simplecontester.backend.taskstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stray27.simplecontester.backend.taskstore.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
