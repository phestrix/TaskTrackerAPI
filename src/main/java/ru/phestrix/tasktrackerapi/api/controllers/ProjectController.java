package ru.phestrix.tasktrackerapi.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.phestrix.tasktrackerapi.api.dto.ProjectDTO;
import ru.phestrix.tasktrackerapi.api.factories.ProjectDtoFactory;
import ru.phestrix.tasktrackerapi.storage.entities.ProjectEntity;
import ru.phestrix.tasktrackerapi.storage.repositories.ProjectRepository;

@RestController
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {

    ProjectDtoFactory projectDtoFactory;
    ProjectRepository projectRepository;
    public static final String CREATE_PROJECT = "api/projects";

    @PostMapping
    public ProjectDTO createProject(@RequestParam String name) {
        ProjectEntity entity =
    }


}
