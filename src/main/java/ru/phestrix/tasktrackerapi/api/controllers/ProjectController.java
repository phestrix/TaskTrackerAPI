package ru.phestrix.tasktrackerapi.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.phestrix.tasktrackerapi.api.controllers.helper.ControllerHelper;
import ru.phestrix.tasktrackerapi.api.dto.AnswerDTO;
import ru.phestrix.tasktrackerapi.api.dto.ProjectDTO;
import ru.phestrix.tasktrackerapi.api.exceptions.NotFoundException;
import ru.phestrix.tasktrackerapi.api.factories.ProjectDtoFactory;
import ru.phestrix.tasktrackerapi.storage.entities.ProjectEntity;
import ru.phestrix.tasktrackerapi.storage.repositories.ProjectRepository;
import ru.phestrix.tasktrackerapi.api.exceptions.BadRequestException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {

    ProjectDtoFactory projectDtoFactory;
    ProjectRepository projectRepository;
    ControllerHelper controllerHelper;
    private static final boolean SUCCESSFUL_DELETION = true;
    private static final boolean DELETION_FAILURE = false;

    public static final String CREATE_OR_EDIT_PROJECT = "api/projects";
    public static final String FETCH_PROJECTS = "/api/projects";
    public static final String DELETE_PROJECT = "api/projects/{project_id}";

    @GetMapping(FETCH_PROJECTS)
    public List<ProjectDTO> fetchProjects(
            @RequestParam(
                    value = "prefix_name",
                    required = false)
            Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);

        return projectStream
                .map(projectDtoFactory::makeProjectDTO)
                .collect(Collectors.toList());
    }

    @PutMapping(CREATE_OR_EDIT_PROJECT)
    public ProjectDTO createOrUpdateProject(
            @RequestParam(value = "project_id", required = false) Optional<Long> optionalProjectId,
            @RequestParam(value = "project_name", required = false) Optional<String> optionalProjectName
    ) {
        optionalProjectName = optionalProjectName.filter(projectName -> !projectName.trim().isEmpty());
        boolean isCreate = !optionalProjectId.isPresent();

        final ProjectEntity projectEntity = optionalProjectId
                .map(controllerHelper::getProjectEntityOrThrowException)
                .orElseGet(() -> ProjectEntity.builder().build());

        if (isCreate && optionalProjectName.isEmpty()) {
            throw new BadRequestException("Project name cant be empty");
        }

        optionalProjectName.ifPresent(projectName -> {
            projectRepository
                    .findProjectEntityByName(projectName)
                    .filter(anotherProject -> Objects.equals(anotherProject.getName(), projectName))
                    .ifPresent(anotherProject -> {
                        throw new BadRequestException("Project with name " + projectName + "already exist");
                    });
            projectEntity.setName(projectName);
        });
        final ProjectEntity savedProject = projectRepository.saveAndFlush(projectEntity);
        return projectDtoFactory.makeProjectDTO(savedProject);
    }

    @DeleteMapping(DELETE_PROJECT)
    public AnswerDTO deleteProject(@PathVariable("project_id") Long projectId) {
        controllerHelper.getProjectEntityOrThrowException(projectId);
        projectRepository.deleteById(projectId);
        return AnswerDTO.makeDefault(SUCCESSFUL_DELETION);
    }


}
