package ru.phestrix.tasktrackerapi.storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.phestrix.tasktrackerapi.storage.entities.ProjectEntity;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Optional<ProjectEntity> findProjectEntityByName(String name);
    Stream<ProjectEntity> StreamAllBy();
    Stream<ProjectEntity> streamAllByNameStartsWithIgnoreCase(String prefixName);
}
