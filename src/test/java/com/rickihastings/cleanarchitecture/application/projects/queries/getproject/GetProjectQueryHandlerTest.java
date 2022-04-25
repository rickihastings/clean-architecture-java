package com.rickihastings.cleanarchitecture.application.projects.queries.getproject;

import an.awesome.pipelinr.Pipelinr;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.middleware.ValidationMiddleware;
import com.rickihastings.cleanarchitecture.seeds.ProjectSeeds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.validation.Validation;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({ SnapshotExtension.class })
public class GetProjectQueryHandlerTest {

    private Expect expect;

    private final IProjectRepository projectRepository = Mockito.mock(IProjectRepository.class);

    private Pipelinr pipeline;

    @BeforeEach
    void setup() {
        var factory = Validation.buildDefaultValidatorFactory();

        pipeline = new Pipelinr()
                .with(() -> Stream.of(new GetProjectQueryHandler(projectRepository)))
                .with(() -> Stream.of(new ValidationMiddleware(factory)));
    }

    @Test
    public void shouldReturnProjectWhenValid() {
        var query = new GetProjectQuery();
        query.setId(1L);
        when(projectRepository.findById(1L)).thenReturn(ProjectSeeds.getProject(1L));

        var result = pipeline.send(query);

        expect.serializer("json").toMatchSnapshot(result);
    }

    @Test
    public void shouldThrowNotFoundErrorWhenNotFound() {
        var query = new GetProjectQuery();
        query.setId(999L);

        assertThrows(NotFoundException.class, () -> pipeline.send(query));
    }
}
