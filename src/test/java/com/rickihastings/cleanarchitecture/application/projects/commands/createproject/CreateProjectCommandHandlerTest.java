package com.rickihastings.cleanarchitecture.application.projects.commands.createproject;

import an.awesome.pipelinr.Pipelinr;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.rickihastings.cleanarchitecture.DefaultSnapshotSerializer;
import com.rickihastings.cleanarchitecture.application.common.exceptions.ValidationException;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.interfaces.services.ICurrentUserService;
import com.rickihastings.cleanarchitecture.application.common.middleware.ValidationMiddleware;
import com.rickihastings.cleanarchitecture.seeds.UserSeeds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.validation.Validation;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({ SnapshotExtension.class })
public class CreateProjectCommandHandlerTest {

    private Expect expect;

    private final ICurrentUserService currentUserService = Mockito.mock(ICurrentUserService.class);
    private final IProjectRepository projectRepository = Mockito.mock(IProjectRepository.class);

    private Pipelinr pipeline;

    @BeforeEach
    void setup() {
        var factory = Validation.buildDefaultValidatorFactory();

        pipeline = new Pipelinr()
                .with(() -> Stream.of(new CreateProjectCommandHandler(currentUserService, projectRepository, pipeline)))
                .with(() -> Stream.of(new ValidationMiddleware(factory)));
    }

    @Test
    public void shouldCreateProjectWhenValid() {
        var command = new CreateProjectCommand();
        command.setTitle("Test Project");
        when(currentUserService.getUser()).thenReturn(UserSeeds.getUser("joebloggs").get());

        var result = pipeline.send(command);

        expect.serializer(DefaultSnapshotSerializer.class).toMatchSnapshot(result);
    }

    @Test
    public void shouldThrowValidationErrorTitleNotNull() {
        var command = new CreateProjectCommand();

        assertThrows(ValidationException.class, () -> pipeline.send(command));
    }

    @Test
    public void shouldThrowValidationErrorTitleNotBlank() {
        var command = new CreateProjectCommand();
        command.setTitle("");

        assertThrows(ValidationException.class, () -> pipeline.send(command));
    }
}
