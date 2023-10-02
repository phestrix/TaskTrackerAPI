package ru.phestrix.tasktrackerapi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "task_state")
public class TaskStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(unique = true)
    String name;

    @Builder.Default
    Instant createdAt = Instant.now();

    @OneToMany
    @JoinColumn()
    @Builder.Default
    List<TaskEntity> tasks = new ArrayList<>();
}
