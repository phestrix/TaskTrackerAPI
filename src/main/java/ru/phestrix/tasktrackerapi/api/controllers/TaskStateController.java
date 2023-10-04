package ru.phestrix.tasktrackerapi.api.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.phestrix.tasktrackerapi.api.dto.TaskDTO;
import ru.phestrix.tasktrackerapi.api.factories.TaskStateDtoFactory;
import ru.phestrix.tasktrackerapi.storage.repositories.TaskStateRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskStateController {
    TaskStateRepository taskStateRepository;
    TaskStateDtoFactory taskStateDtoFactory;

    public static final String GET_TASKS_STATES = "api/projects/{project_id}/tasks-states";
    public static final String CREATE_TASKS_STATE = "api/projects/{project_id}/tasks-states";
    public static final String UPDATE_TASK_STATE = "api/task-states/{task_state_id}";
    public static final String CHANGE_TASK_STATE_POSITION = "/api/task-states/{task_state_id}/position/change";
    public static final String DELETE_TASK_STATE = "api/task-states/{task_state_id}";

    @GetMapping(GET_TASKS_STATES)
    public List<TaskDTO> getTaskStates() {

    }
}
