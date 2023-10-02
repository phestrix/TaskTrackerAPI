package ru.phestrix.tasktrackerapi.storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.phestrix.tasktrackerapi.storage.entities.TaskStateEntity;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {
}
