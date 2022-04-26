package com.rickihastings.cleanarchitecture.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User user;

    private Instant createdAt;
    private Instant updatedAt;

    @NonNull
    private String title;

    private boolean deleted = false;
}
