package ru.phestrix.tasktrackerapi.storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.phestrix.tasktrackerapi.storage.entities.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
