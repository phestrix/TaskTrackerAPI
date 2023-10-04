package ru.phestrix.tasktrackerapi.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.phestrix.tasktrackerapi.api.controllers.helper.ControllerHelper;
import ru.phestrix.tasktrackerapi.api.dto.TaskDTO;
import ru.phestrix.tasktrackerapi.api.dto.TaskStateDTO;
import ru.phestrix.tasktrackerapi.api.exceptions.BadRequestException;
import ru.phestrix.tasktrackerapi.api.factories.TaskStateDtoFactory;
import ru.phestrix.tasktrackerapi.storage.entities.ProjectEntity;
import ru.phestrix.tasktrackerapi.storage.entities.TaskStateEntity;
import ru.phestrix.tasktrackerapi.storage.repositories.TaskStateRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskStateController {
    TaskStateRepository taskStateRepository;
    TaskStateDtoFactory taskStateDtoFactory;
    ControllerHelper controllerHelper;
    public static final String GET_TASKS_STATES = "api/projects/{project_id}/tasks-states";
    public static final String CREATE_TASKS_STATE = "api/projects/{project_id}/tasks-states";
    public static final String UPDATE_TASK_STATE = "api/task-states/{task_state_id}";
    public static final String CHANGE_TASK_STATE_POSITION = "/api/task-states/{task_state_id}/position/change";
    public static final String DELETE_TASK_STATE = "api/task-states/{task_state_id}";

    @GetMapping(GET_TASKS_STATES)
    public List<TaskStateDTO> getTaskStates(@PathVariable(name = "project_id") Long projectId) {
        ProjectEntity projectEntity = controllerHelper.getProjectEntityOrThrowException(projectId);
        return projectEntity.getTaskStates()
                .stream()
                .map(taskStateDtoFactory::makeTaskStateDTO)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_TASKS_STATE)
    public TaskStateDTO createTaskState(
            @PathVariable(name = "project_id") Long projectId,
            @RequestParam(name = "task_state_name") String taskStateName
    ){
        if (taskStateName.trim().isEmpty()) {
            throw new BadRequestException("Task state name can't be empty.");
        }

        ProjectEntity project = controllerHelper.getProjectEntityOrThrowException(projectId);

        Optional<TaskStateEntity> optionalAnotherTaskState = Optional.empty();

        for (TaskStateEntity taskState: project.getTaskStates()) {

            if (taskState.getName().equalsIgnoreCase(taskStateName)) {
                throw new BadRequestException(String.format("Task state \"%s\" already exists.", taskStateName));
            }

            if (taskState.getRightTaskState().isEmpty()) {
                optionalAnotherTaskState = Optional.of(taskState);
                break;
            }
        }

        TaskStateEntity taskState = taskStateRepository.saveAndFlush(
                TaskStateEntity.builder()
                        .name(taskStateName)
                        .project(project)
                        .build()
        );

        optionalAnotherTaskState
                .ifPresent(anotherTaskState -> {

                    taskState.setLeftTaskState(anotherTaskState);

                    anotherTaskState.setRightTaskState(taskState);

                    taskStateRepository.saveAndFlush(anotherTaskState);
                });

        final TaskStateEntity savedTaskState = taskStateRepository.saveAndFlush(taskState);

        return taskStateDtoFactory.makeTaskStateDTO(savedTaskState);
    }
}
