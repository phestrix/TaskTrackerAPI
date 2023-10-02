package ru.phestrix.tasktrackerapi.storage.entities;

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
            @JoinColumn(name = "project_id", referencedColumnName = "id")
    List<TaskStateEntity> taskStates = new ArrayList<>();
}
