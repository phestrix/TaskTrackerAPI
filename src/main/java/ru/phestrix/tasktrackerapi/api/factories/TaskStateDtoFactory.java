package ru.phestrix.tasktrackerapi.api.factories;

import org.springframework.stereotype.Component;
import ru.phestrix.tasktrackerapi.api.dto.TaskStateDTO;
import ru.phestrix.tasktrackerapi.storage.entities.TaskStateEntity;

@Component
public class TaskStateDtoFactory {
    public TaskStateDTO makeTaskStateDTO(TaskStateEntity entity) {
        return TaskStateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .ordinal(entity.getOrdinal())
                .build();

    }
}
