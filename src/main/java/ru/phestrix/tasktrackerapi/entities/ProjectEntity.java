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
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(unique = true)
    String name;

    @Builder.Default
    Instant createdAt = Instant.now();

    String description;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY)
    List<TaskStateEntity> taskStates = new ArrayList<>();
}
