package com.rickihastings.cleanarchitecture.application.projects.queries.getprojects;

import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import io.jkratz.mediator.core.RequestHandler;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetProjectsQueryHandler implements RequestHandler<GetProjectsQuery, List<ProjectDto>> {

    private final IProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public GetProjectsQueryHandler(IProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProjectDto> handle(@NonNull GetProjectsQuery query) {
        return projectRepository
                .findAll()
                .stream()
                .map(project -> modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }
}
