package com.rickihastings.cleanarchitecture.application.projects.queries.getproject;

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

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetProjectQueryHandler implements Command.Handler<GetProjectQuery, ProjectDto> {

    private final IProjectRepository projectRepository;

    PropertyMap<Project, ProjectDto> productMap = new PropertyMap<>() {
        protected void configure() {
            map().setCreatedBy(source.getUser().getName());
        }
    };

    @Override
    public ProjectDto handle(@NonNull GetProjectQuery query) throws NotFoundException {
        var modelMapper = new ModelMapper();
        modelMapper.addMappings(productMap);

        var id = query.getId();
        var project = projectRepository.findById(id);

        if (project.isEmpty()) {
            throw new NotFoundException(String.format("Product %d not found", id));
        }

        return modelMapper.map(project.get(), ProjectDto.class);
    }
}
