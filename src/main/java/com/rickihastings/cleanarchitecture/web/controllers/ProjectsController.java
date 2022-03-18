package com.rickihastings.cleanarchitecture.web.controllers;

import com.rickihastings.cleanarchitecture.application.projects.commands.createproject.CreateProjectCommand;
import com.rickihastings.cleanarchitecture.application.projects.queries.getprojects.GetProjectsQuery;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import io.jkratz.mediator.core.Mediator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/projects")
public class ProjectsController {

    private final Mediator mediator;

    public ProjectsController(Mediator mediator) {
        this.mediator = mediator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectDto>> listProjects() {
        return new ResponseEntity<>(mediator.dispatch(new GetProjectsQuery()), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ProjectDto> createProject(@RequestBody CreateProjectCommand command) {
        return new ResponseEntity<>(mediator.dispatch(command), HttpStatus.CREATED);
    }
}
