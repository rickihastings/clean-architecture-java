package com.rickihastings.cleanarchitecture.infrastructure.repositories;

import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends IProjectRepository, JpaRepository<Project, Long> {
    @Query(value = "SELECT * FROM projects p WHERE p.deleted = false", nativeQuery = true)
    List<Project> findAll();

    @Query(value = "SELECT * FROM projects p WHERE p.id = ? AND p.deleted = false", nativeQuery = true)
    Optional<Project> findById(@NonNull Long id);

    Project save(@NonNull Project project);
}

