package ru.phestrix.tasktrackerapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.phestrix.tasktrackerapi.entities.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
