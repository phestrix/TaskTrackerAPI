package ru.phestrix.tasktrackerapi.api.controllers.helper;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.phestrix.tasktrackerapi.api.exceptions.NotFoundException;
import ru.phestrix.tasktrackerapi.storage.entities.ProjectEntity;
import ru.phestrix.tasktrackerapi.storage.repositories.ProjectRepository;

@RequiredArgsConstructor
@Transactional
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ControllerHelper {
    ProjectRepository projectRepository;
    public ProjectEntity getProjectEntityOrThrowException(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with id: " + projectId + "doesnt exist"));
    }
}
