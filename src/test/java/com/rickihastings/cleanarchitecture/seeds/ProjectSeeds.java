package com.rickihastings.cleanarchitecture.seeds;

import com.rickihastings.cleanarchitecture.domain.Project;

import java.time.Instant;
import java.util.List;

public class ProjectSeeds {

    public static List<Project> getProjects() {
        var user = UserSeeds.getUser("joebloggs").get();
        var date = Instant.parse("2007-12-03T10:15:30+01:00");

        var project = new Project();
        project.setUser(user);
        project.setCreatedAt(date);
        project.setTitle("Initial Project");

        var secondProject = new Project();
        secondProject.setUser(user);
        secondProject.setCreatedAt(date);
        secondProject.setTitle("Second Project");

        return List.of(project, secondProject);
    }
}
