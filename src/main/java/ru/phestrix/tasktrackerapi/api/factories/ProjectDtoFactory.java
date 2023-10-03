package ru.phestrix.tasktrackerapi.api.factories;

import org.springframework.stereotype.Component;
import ru.phestrix.tasktrackerapi.api.dto.ProjectDTO;
import ru.phestrix.tasktrackerapi.storage.entities.ProjectEntity;


//Not a factory pattern but a convert from entity to dto
//Just a layer between them
@Component
public class ProjectDtoFactory {
    public ProjectDTO makeProjectDTO(ProjectEntity entity) {
        return ProjectDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
