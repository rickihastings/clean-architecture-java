package com.rickihastings.cleanarchitecture.application.common.interfaces.repositories;

import com.rickihastings.cleanarchitecture.domain.Project;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IProjectRepository {
    List<Project> findByArchivedFalse();
    Project save(Project project);
}
