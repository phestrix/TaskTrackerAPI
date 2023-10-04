package ru.phestrix.tasktrackerapi.api.factories;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.phestrix.tasktrackerapi.api.dto.TaskStateDTO;
import ru.phestrix.tasktrackerapi.storage.entities.TaskStateEntity;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class TaskStateDtoFactory {
    TaskDtoFactory taskDtoFactory;
    public TaskStateDTO makeTaskStateDTO(TaskStateEntity entity) {
        return TaskStateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .leftTaskState(entity.getLeftTaskState().map(TaskStateEntity::getId).orElse(null))
                .rightTaskState(entity.getRightTaskState().map(TaskStateEntity::getId).orElse(null))
                .tasks(
                        entity
                                .getTasks()
                                .stream()
                                .map(taskDtoFactory::makeTaskDTO)
                                .collect(Collectors.toList())
                )
                .build();

    }
}
