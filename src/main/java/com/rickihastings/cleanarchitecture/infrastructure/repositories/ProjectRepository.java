package com.rickihastings.cleanarchitecture.infrastructure.repositories;

import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.domain.entities.Project;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends IProjectRepository, JpaRepository<Project, Long> {
    @NotNull
    @Query(value = "SELECT * FROM projects p WHERE p.deleted = false", nativeQuery = true)
    List<Project> findAll();

    @NotNull
    @Query(value = "SELECT * FROM projects p WHERE p.id = ? AND p.deleted = false", nativeQuery = true)
    Optional<Project> findById(@NonNull Long id);

    @NotNull
    Project save(@NonNull Project project);
}

