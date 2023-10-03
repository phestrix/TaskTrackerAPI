package ru.phestrix.tasktrackerapi.api.factories;

import org.springframework.stereotype.Component;
import ru.phestrix.tasktrackerapi.api.dto.TaskDTO;
import ru.phestrix.tasktrackerapi.storage.entities.TaskEntity;

@Component
public class TaskDtoFactory {
    public TaskDTO makeTaskDTO(TaskEntity entity) {
        return TaskDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
