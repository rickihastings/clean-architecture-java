package com.rickihastings.cleanarchitecture.web.controllers;

import an.awesome.pipelinr.Pipeline;
import com.rickihastings.cleanarchitecture.application.projects.commands.createproject.CreateProjectCommand;
import com.rickihastings.cleanarchitecture.application.projects.queries.getprojects.GetProjectsQuery;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/projects")
public class ProjectsController extends ApiControllerBase {

    protected ProjectsController(Pipeline pipeline) {
        super(pipeline);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectDto> listProjects() {
        return pipeline.send(new GetProjectsQuery());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@RequestBody CreateProjectCommand command) {
        return pipeline.send(command);
    }
}
