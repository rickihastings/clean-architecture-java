package com.rickihastings.cleanarchitecture.application.projects.commands.deleteproject;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.interfaces.services.IEventsService;
import com.rickihastings.cleanarchitecture.domain.events.ProjectDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteProjectCommandHandler implements Command.Handler<DeleteProjectCommand, DeleteProjectDto> {

    private final IEventsService eventsService;
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

        eventsService.publish(new ProjectDeletedEvent(project));

        return new DeleteProjectDto();
    }
}
