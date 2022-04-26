package com.rickihastings.cleanarchitecture.application.projects.commands.createproject;

import an.awesome.pipelinr.Pipelinr;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit4.SnapshotClassRule;
import au.com.origin.snapshots.junit4.SnapshotRule;
import com.rickihastings.cleanarchitecture.DefaultSnapshotSerializer;
import com.rickihastings.cleanarchitecture.application.common.exceptions.UnauthorizedException;
import com.rickihastings.cleanarchitecture.application.common.exceptions.ValidationException;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.interfaces.services.ICurrentUserService;
import com.rickihastings.cleanarchitecture.application.common.interfaces.services.IEventsService;
import com.rickihastings.cleanarchitecture.application.common.middleware.AuthenticationMiddleware;
import com.rickihastings.cleanarchitecture.application.common.middleware.ValidationMiddleware;
import com.rickihastings.cleanarchitecture.seeds.UserSeeds;
import com.rickihastings.cleanarchitecture.web.Application;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WithMockUser
public class CreateProjectCommandHandlerTest {

    @ClassRule
    public static SnapshotClassRule snapshotClassRule = new SnapshotClassRule();

    @Rule
    public SnapshotRule snapshotRule = new SnapshotRule(snapshotClassRule);

    private Expect expect;

    private final ICurrentUserService currentUserService = Mockito.mock(ICurrentUserService.class);
    private final IEventsService eventsService = Mockito.mock(IEventsService.class);
    private final IProjectRepository projectRepository = Mockito.mock(IProjectRepository.class);
    private Pipelinr pipeline;

    @Before
    public void setup() {
        var factory = Validation.buildDefaultValidatorFactory();

        pipeline = new Pipelinr()
                .with(() -> Stream.of(new CreateProjectCommandHandler(currentUserService, eventsService, projectRepository)))
                .with(() -> Stream.of(new AuthenticationMiddleware(), new ValidationMiddleware(factory)));
    }

    @Test
    public void shouldCreateProjectWhenValid() {
        var command = new CreateProjectCommand();
        command.setTitle("Test Project");
        when(currentUserService.getUser()).thenReturn(UserSeeds.getUser("joebloggs").get());

        var result = pipeline.send(command);

        expect.serializer(DefaultSnapshotSerializer.class).toMatchSnapshot(result);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationErrorTitleNotNull() {
        var command = new CreateProjectCommand();

        pipeline.send(command);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationErrorTitleNotBlank() {
        var command = new CreateProjectCommand();
        command.setTitle("");

        pipeline.send(command);
    }

    @Test(expected = UnauthorizedException.class)
    @WithAnonymousUser
    public void shouldThrowUnauthorizedExceptionWhenAnonymous() {
        var command = new CreateProjectCommand();

        pipeline.send(command);
    }
}
