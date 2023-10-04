package ru.phestrix.tasktrackerapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskStateDTO {
    @NonNull
    Long id;
    @NonNull
    String name;
    @NonNull
    Long ordinal;
    @NonNull
    @JsonProperty("created_at")
    Instant createdAt;

    @JsonProperty("left_task_state_id")
    Long leftTaskState;

    @JsonProperty("right_task_state_id")
    Long rightTaskState;
}
