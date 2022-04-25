package com.rickihastings.cleanarchitecture.application.projects.commands.deleteproject;

import an.awesome.pipelinr.Command;
import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.projects.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DeleteProjectCommandHandler implements Command.Handler<DeleteProjectCommand, DeleteProjectDto> {

    private final IProjectRepository projectRepository;

    @Override
    public DeleteProjectDto handle(@NonNull DeleteProjectCommand command) {
        var id = command.getId();
        var optionalProject = projectRepository.findById(id);

        if (optionalProject.isEmpty()) {
            throw new NotFoundException(String.format("Product %d not found", id));
        }

        var project = optionalProject.get();
        project.setDeleted(true);
        projectRepository.save(project);

        return new DeleteProjectDto();
    }
}
