package com.rickihastings.cleanarchitecture.seeds;

import com.rickihastings.cleanarchitecture.domain.Project;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProjectSeeds {

    public static List<Project> getProjects() {
        var user = UserSeeds.getUser("joebloggs").get();
        var date = Instant.parse("2007-12-03T10:15:30+01:00");

        var project = new Project();
        project.setId(1L);
        project.setUser(user);
        project.setCreatedAt(date);
        project.setTitle("Initial Project");

        var secondProject = new Project();
        secondProject.setId(2L);
        secondProject.setUser(user);
        secondProject.setCreatedAt(date);
        secondProject.setTitle("Second Project");

        return List.of(project, secondProject);
    }

    public static Optional<Project> getProject(Long id) {
        var projects = getProjects();

        return projects
                .stream()
                .filter(project -> Objects.equals(project.getId(), id))
                .findFirst();
    }
}
