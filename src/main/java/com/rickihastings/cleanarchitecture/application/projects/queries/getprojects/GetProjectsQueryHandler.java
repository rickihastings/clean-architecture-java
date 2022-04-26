package com.rickihastings.cleanarchitecture.application.projects.queries.getprojects;

import an.awesome.pipelinr.Command;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import com.rickihastings.cleanarchitecture.domain.entities.Project;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetProjectsQueryHandler implements Command.Handler<GetProjectsQuery, List<ProjectDto>> {

    private final IProjectRepository projectRepository;

    PropertyMap<Project, ProjectDto> productMap = new PropertyMap<>() {
        protected void configure() {
            map().setCreatedBy(source.getUser().getName());
        }
    };

    @Override
    public List<ProjectDto> handle(@NonNull GetProjectsQuery query) {
        var modelMapper = new ModelMapper();
        modelMapper.addMappings(productMap);

        return projectRepository
                .findAll()
                .stream()
                .map(project -> modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }
}
