package com.rickihastings.cleanarchitecture.application.projects.commands.updateproject;

import an.awesome.pipelinr.Pipelinr;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.rickihastings.cleanarchitecture.DefaultSnapshotSerializer;
import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import com.rickihastings.cleanarchitecture.application.common.exceptions.ValidationException;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.middleware.ValidationMiddleware;
import com.rickihastings.cleanarchitecture.application.projects.commands.createproject.CreateProjectCommand;
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
public class UpdateProjectCommandHandlerTest {

    private Expect expect;

    private final IProjectRepository projectRepository = Mockito.mock(IProjectRepository.class);

    private Pipelinr pipeline;

    @BeforeEach
    void setup() {
        var factory = Validation.buildDefaultValidatorFactory();

        pipeline = new Pipelinr()
                .with(() -> Stream.of(new UpdateProjectCommandHandler(projectRepository)))
                .with(() -> Stream.of(new ValidationMiddleware(factory)));
    }

    @Test
    public void shouldUpdateProjectWhenValid() {
        var id = 1L;
        var command = new UpdateProjectCommand();
        command.setId(id);
        command.setTitle("Updated Title");
        when(projectRepository.findById(id)).thenReturn(ProjectSeeds.getProject(id));

        var result = pipeline.send(command);

        verify(projectRepository, times(1)).save(any());
        expect.serializer(DefaultSnapshotSerializer.class).toMatchSnapshot(result);
    }

    @Test
    public void shouldThrowNotFoundErrorWhenNotFound() {
        var command = new UpdateProjectCommand();
        command.setId(999L);
        command.setTitle("Updated Title");

        assertThrows(NotFoundException.class, () -> {
            pipeline.send(command);

            verify(projectRepository, never()).save(any());
        });
    }

    @Test
    public void shouldThrowValidationErrorTitleNotNull() {
        var command = new UpdateProjectCommand();
        command.setId(1L);

        assertThrows(ValidationException.class, () -> pipeline.send(command));
    }

    @Test
    public void shouldThrowValidationErrorTitleNotBlank() {
        var command = new UpdateProjectCommand();
        command.setId(1L);
        command.setTitle("");

        assertThrows(ValidationException.class, () -> pipeline.send(command));
    }
}
