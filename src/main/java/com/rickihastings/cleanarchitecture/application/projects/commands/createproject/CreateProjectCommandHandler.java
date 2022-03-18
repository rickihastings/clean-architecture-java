package com.rickihastings.cleanarchitecture.application.projects.commands.createproject;

import an.awesome.pipelinr.Command;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import com.rickihastings.cleanarchitecture.domain.Project;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CreateProjectCommandHandler implements Command.Handler<CreateProjectCommand, ProjectDto> {

    private final IProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public CreateProjectCommandHandler(IProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProjectDto handle(@NonNull CreateProjectCommand command) {
        Project project = new Project();
        project.setTitle(command.getTitle());

        projectRepository.save(project);

        return modelMapper.map(project, ProjectDto.class);
    }
}
