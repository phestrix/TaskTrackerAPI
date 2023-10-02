package ru.phestrix.tasktrackerapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.phestrix.tasktrackerapi.entities.TaskStateEntity;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {
}
