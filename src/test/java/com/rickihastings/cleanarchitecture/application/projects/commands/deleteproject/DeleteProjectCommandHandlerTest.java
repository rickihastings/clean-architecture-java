package com.rickihastings.cleanarchitecture.application.projects.commands.deleteproject;

import an.awesome.pipelinr.Pipelinr;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit4.SnapshotClassRule;
import au.com.origin.snapshots.junit4.SnapshotRule;
import com.rickihastings.cleanarchitecture.DefaultSnapshotSerializer;
import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import com.rickihastings.cleanarchitecture.application.common.exceptions.UnauthorizedException;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WithMockUser
public class DeleteProjectCommandHandlerTest {

    @ClassRule
    public static SnapshotClassRule snapshotClassRule = new SnapshotClassRule();

    @Rule
    public SnapshotRule snapshotRule = new SnapshotRule(snapshotClassRule);

    private Expect expect;

    private final IProjectRepository projectRepository = Mockito.mock(IProjectRepository.class);
    private Pipelinr pipeline;

    @Before
    public void setup() {
        var factory = Validation.buildDefaultValidatorFactory();

        pipeline = new Pipelinr()
                .with(() -> Stream.of(new DeleteProjectCommandHandler(projectRepository, pipeline)))
                .with(() -> Stream.of(new AuthenticationMiddleware(), new ValidationMiddleware(factory)));
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

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundErrorWhenNotFound() {
        var command = new DeleteProjectCommand();
        command.setId(999L);

        pipeline.send(command);

        verify(projectRepository, never()).save(any());
    }

    @Test(expected = UnauthorizedException.class)
    @WithAnonymousUser
    public void shouldThrowUnauthorizedExceptionWhenAnonymous() {
        var command = new DeleteProjectCommand();

        pipeline.send(command);
    }
}
