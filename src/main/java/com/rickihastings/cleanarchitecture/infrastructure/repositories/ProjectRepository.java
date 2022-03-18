package com.rickihastings.cleanarchitecture.infrastructure.repositories;

import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends IProjectRepository, JpaRepository<Project, Long> {
    List<Project> findAll();
    Project save(Project project);
}

