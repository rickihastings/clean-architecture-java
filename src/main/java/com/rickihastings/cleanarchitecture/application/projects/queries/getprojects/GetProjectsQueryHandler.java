package com.rickihastings.cleanarchitecture.application.projects.queries.getprojects;

import an.awesome.pipelinr.Command;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import com.rickihastings.cleanarchitecture.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetProjectsQueryHandler implements Command.Handler<GetProjectsQuery, List<ProjectDto>> {

    private final User currentUser;
    private final ModelMapper modelMapper;
    private final IProjectRepository projectRepository;

    public GetProjectsQueryHandler(User currentUser, ModelMapper modelMapper, IProjectRepository projectRepository) {
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectDto> handle(@NonNull GetProjectsQuery query) {
        System.out.println("GetProjectsQueryHandler");
        System.out.println(currentUser.getUsername());

        return projectRepository
                .findByArchivedFalse()
                .stream()
                .map(project -> modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }
}
