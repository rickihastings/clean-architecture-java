package com.rickihastings.cleanarchitecture.web.controllers;

import an.awesome.pipelinr.Pipeline;
import com.rickihastings.cleanarchitecture.application.projects.commands.createproject.CreateProjectCommand;
import com.rickihastings.cleanarchitecture.application.projects.commands.deleteproject.DeleteProjectCommand;
import com.rickihastings.cleanarchitecture.application.projects.commands.deleteproject.DeleteProjectDto;
import com.rickihastings.cleanarchitecture.application.projects.commands.updateproject.UpdateProjectCommand;
import com.rickihastings.cleanarchitecture.application.projects.queries.getproject.GetProjectQuery;
import com.rickihastings.cleanarchitecture.application.projects.queries.getprojects.GetProjectsQuery;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@RequestBody CreateProjectCommand command) {
        return pipeline.send(command);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto getProject(@PathVariable Long id) {
        var query = new GetProjectQuery();
        query.setId(id);

        return pipeline.send(query);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ProjectDto updateProject(@PathVariable Long id, @RequestBody UpdateProjectCommand command) {
        command.setId(id);

        return pipeline.send(command);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public DeleteProjectDto deleteProject(@PathVariable Long id) {
        var command = new DeleteProjectCommand();
        command.setId(id);

        return pipeline.send(command);
    }
}
