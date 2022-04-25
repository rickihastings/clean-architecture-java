package com.rickihastings.cleanarchitecture.application.projects.commands.deleteproject;

import an.awesome.pipelinr.Pipelinr;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.rickihastings.cleanarchitecture.DefaultSnapshotSerializer;
import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.middleware.ValidationMiddleware;
import com.rickihastings.cleanarchitecture.application.projects.commands.updateproject.UpdateProjectCommand;
import com.rickihastings.cleanarchitecture.seeds.ProjectSeeds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.validation.Validation;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({ SnapshotExtension.class })
public class DeleteProjectCommandHandlerTest {

    private Expect expect;

    private final IProjectRepository projectRepository = Mockito.mock(IProjectRepository.class);

    private Pipelinr pipeline;

    @BeforeEach
    void setup() {
        var factory = Validation.buildDefaultValidatorFactory();

        pipeline = new Pipelinr()
                .with(() -> Stream.of(new DeleteProjectCommandHandler(projectRepository)))
                .with(() -> Stream.of(new ValidationMiddleware(factory)));
    }

    @Test
    public void shouldDeleteProjectWhenValid() {
        var id = 1L;
        var command = new DeleteProjectCommand();
        command.setId(id);
        when(projectRepository.findById(id)).thenReturn(ProjectSeeds.getProject(id));

        var result = pipeline.send(command);

        verify(projectRepository, times(1)).save(any());
        expect.serializer(DefaultSnapshotSerializer.class).toMatchSnapshot(result);
    }

    @Test
    public void shouldThrowNotFoundErrorWhenNotFound() {
        var command = new DeleteProjectCommand();
        command.setId(999L);

        assertThrows(NotFoundException.class, () -> {
            pipeline.send(command);

            verify(projectRepository, never()).save(any());
        });
    }
}
