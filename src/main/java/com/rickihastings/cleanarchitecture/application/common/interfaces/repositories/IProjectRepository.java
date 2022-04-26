package com.rickihastings.cleanarchitecture.application.common.interfaces.repositories;

import com.rickihastings.cleanarchitecture.domain.entities.Project;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProjectRepository {
    List<Project> findAll();
    Optional<Project> findById(Long id);
    Project save(Project project);
}
