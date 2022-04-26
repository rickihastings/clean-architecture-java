package com.rickihastings.cleanarchitecture.application.projects.commands.createproject;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.interfaces.services.ICurrentUserService;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import com.rickihastings.cleanarchitecture.domain.entities.Project;
import com.rickihastings.cleanarchitecture.domain.events.ProjectCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CreateProjectCommandHandler implements Command.Handler<CreateProjectCommand, ProjectDto> {

    private final ICurrentUserService currentUserService;
    private final IProjectRepository projectRepository;
    private final Pipeline pipeline;

    @Override
    public ProjectDto handle(@NonNull CreateProjectCommand command) {
        var modelMapper = new ModelMapper();
        var now = Instant.now();

        Project project = new Project();
        project.setCreatedAt(now);
        project.setUpdatedAt(now);
        project.setTitle(command.getTitle());
        project.setUser(currentUserService.getUser());

        projectRepository.save(project);

        pipeline.send(new ProjectCreatedEvent(project));

        return modelMapper.map(project, ProjectDto.class);
    }
}
