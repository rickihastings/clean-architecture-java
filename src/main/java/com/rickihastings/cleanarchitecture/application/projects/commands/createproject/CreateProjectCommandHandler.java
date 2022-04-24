package com.rickihastings.cleanarchitecture.application.projects.commands.createproject;

import an.awesome.pipelinr.Command;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.interfaces.services.ICurrentUserService;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import com.rickihastings.cleanarchitecture.domain.Project;
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

    @Override
    public ProjectDto handle(@NonNull CreateProjectCommand command) {
        var modelMapper = new ModelMapper();

        Project project = new Project();
        project.setCreatedAt(Instant.now());
        project.setTitle(command.getTitle());
        project.setUser(currentUserService.getUser());

        projectRepository.save(project);

        return modelMapper.map(project, ProjectDto.class);
    }
}
