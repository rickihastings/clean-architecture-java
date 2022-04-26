package com.rickihastings.cleanarchitecture.application.projects.commands.updateproject;

import an.awesome.pipelinr.Pipelinr;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit4.SnapshotClassRule;
import au.com.origin.snapshots.junit4.SnapshotRule;
import com.rickihastings.cleanarchitecture.DefaultSnapshotSerializer;
import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import com.rickihastings.cleanarchitecture.application.common.exceptions.ValidationException;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.interfaces.services.IEventsService;
import com.rickihastings.cleanarchitecture.application.common.middleware.AuthenticationMiddleware;
import com.rickihastings.cleanarchitecture.application.common.middleware.ValidationMiddleware;
import com.rickihastings.cleanarchitecture.seeds.ProjectSeeds;
import com.rickihastings.cleanarchitecture.web.Application;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WithMockUser
public class UpdateProjectCommandHandlerTest {

    @ClassRule
    public static SnapshotClassRule snapshotClassRule = new SnapshotClassRule();

    @Rule
    public SnapshotRule snapshotRule = new SnapshotRule(snapshotClassRule);

    private Expect expect;

    private final IEventsService eventsService = Mockito.mock(IEventsService.class);
    private final IProjectRepository projectRepository = Mockito.mock(IProjectRepository.class);
    private Pipelinr pipeline;

    @Before
    public void setup() {
        var factory = Validation.buildDefaultValidatorFactory();

        pipeline = new Pipelinr()
                .with(() -> Stream.of(new UpdateProjectCommandHandler(eventsService, projectRepository)))
                .with(() -> Stream.of(new AuthenticationMiddleware(), new ValidationMiddleware(factory)));
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

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundErrorWhenNotFound() {
        var command = new UpdateProjectCommand();
        command.setId(999L);
        command.setTitle("Updated Title");

        pipeline.send(command);

        verify(projectRepository, never()).save(any());
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationErrorTitleNotNull() {
        var command = new UpdateProjectCommand();
        command.setId(1L);

        pipeline.send(command);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationErrorTitleNotBlank() {
        var command = new UpdateProjectCommand();
        command.setId(1L);
        command.setTitle("");

        pipeline.send(command);
    }
}
