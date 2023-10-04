package ru.phestrix.tasktrackerapi.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
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
    private static final boolean SUCCESSFUL_DELETION = true;
    private static final boolean DELETION_FAILURE = false;
    public static final String CREATE_PROJECT = "api/projects";
    public static final String FETCH_PROJECTS = "/api/projects";
    public static final String EDIT_PROJECT = "api/projects/{project_id}";
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

    @PostMapping(CREATE_PROJECT)
    public ProjectDTO createProject(@RequestParam String name) {
        projectRepository.findProjectEntityByName(name)
                .ifPresent(project -> {
                            throw new BadRequestException("project with " + name + " already exist");
                        }
                );
        ProjectEntity entity = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(name)
                        .build()
        );
        return projectDtoFactory.makeProjectDTO(entity);
    }

    @PatchMapping(EDIT_PROJECT)
    public ProjectDTO editProject(
            @PathVariable("project_id") Long projectId,
            @RequestParam String name) {
        if (name.trim().isEmpty()) {
            throw new BadRequestException("name cant be empty");
        }

        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() ->
                new NotFoundException("Project with id " + projectId + "doesn't exist"));

        projectRepository.findProjectEntityByName(name)
                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException("project with " + name + " already exist");
                });
        project.setName(name);
        project = projectRepository.saveAndFlush(project);
        return projectDtoFactory.makeProjectDTO(project);
    }

    @DeleteMapping(DELETE_PROJECT)
    public AnswerDTO deleteProject(@PathVariable("project_id") Long projectId) {
        projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with id: " + projectId + "doesnt exist"));
        projectRepository.deleteById(projectId);
        return AnswerDTO.makeDefault(SUCCESSFUL_DELETION);
    }
}
