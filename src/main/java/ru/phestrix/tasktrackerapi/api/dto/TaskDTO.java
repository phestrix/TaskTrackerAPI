package ru.phestrix.tasktrackerapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Name;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDTO {
    @NonNull
    Long id;
    @NonNull
    String name;
    @NonNull
    @JsonProperty("created_at")
    Instant createdAt;
    @NonNull
    String description;
}
