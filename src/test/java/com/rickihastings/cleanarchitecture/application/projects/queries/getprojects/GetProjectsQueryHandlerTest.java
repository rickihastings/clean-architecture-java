package com.rickihastings.cleanarchitecture.application.projects.queries.getprojects;

import an.awesome.pipelinr.Pipelinr;
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.middleware.ValidationMiddleware;
import com.rickihastings.cleanarchitecture.seeds.ProjectSeeds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.validation.Validation;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith({ SnapshotExtension.class })
public class GetProjectsQueryHandlerTest {

    private Expect expect;

    private final IProjectRepository projectRepository = Mockito.mock(IProjectRepository.class);

    private Pipelinr pipeline;

    @BeforeEach
    void setup() {
        var factory = Validation.buildDefaultValidatorFactory();

        pipeline = new Pipelinr()
                .with(() -> Stream.of(new GetProjectsQueryHandler(projectRepository)))
                .with(() -> Stream.of(new ValidationMiddleware(factory)));
    }

    @Test
    public void shouldReturnProjectsWhenValid() {
        when(projectRepository.findByArchivedFalse()).thenReturn(ProjectSeeds.getProjects());

        var result = pipeline.send(new GetProjectsQuery());

        expect.serializer("json").toMatchSnapshot(result);
    }
}
