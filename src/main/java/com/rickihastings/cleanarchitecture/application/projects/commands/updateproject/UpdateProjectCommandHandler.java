package com.rickihastings.cleanarchitecture.application.projects.commands.updateproject;

import an.awesome.pipelinr.Command;
import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import com.rickihastings.cleanarchitecture.domain.Project;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UpdateProjectCommandHandler implements Command.Handler<UpdateProjectCommand, ProjectDto> {

    private final IProjectRepository projectRepository;

    PropertyMap<Project, ProjectDto> productMap = new PropertyMap<>() {
        protected void configure() {
            map().setCreatedBy(source.getUser().getName());
        }
    };

    @Override
    public ProjectDto handle(@NonNull UpdateProjectCommand command) {
        var id = command.getId();
        var optionalProject = projectRepository.findById(id);

        if (optionalProject.isEmpty()) {
            throw new NotFoundException(String.format("Product %d not found", id));
        }

        var modelMapper = new ModelMapper();
        modelMapper.addMappings(productMap);

        var project = optionalProject.get();
        project.setTitle(command.getTitle());
        project.setUpdatedAt(Instant.now());
        projectRepository.save(project);

        return modelMapper.map(project, ProjectDto.class);
    }
}
