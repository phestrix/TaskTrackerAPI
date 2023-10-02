package ru.phestrix.tasktrackerapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.phestrix.tasktrackerapi.entities.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
