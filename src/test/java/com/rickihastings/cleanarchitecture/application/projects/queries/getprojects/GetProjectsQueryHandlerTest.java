package com.rickihastings.cleanarchitecture.application.projects.queries.getprojects;

import an.awesome.pipelinr.Pipelinr;
import au.com.origin.snapshots.Expect;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.middleware.ValidationMiddleware;
import com.rickihastings.cleanarchitecture.seeds.ProjectSeeds;
import com.rickihastings.cleanarchitecture.utils.UnitTestBase;
import com.rickihastings.cleanarchitecture.web.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GetProjectsQueryHandlerTest extends UnitTestBase {

    private Expect expect;

    private final IProjectRepository projectRepository = Mockito.mock(IProjectRepository.class);
    private Pipelinr pipeline;

    @Before
    public void setup() {
        var factory = Validation.buildDefaultValidatorFactory();

        pipeline = new Pipelinr()
                .with(() -> Stream.of(new GetProjectsQueryHandler(projectRepository)))
                .with(() -> Stream.of(new ValidationMiddleware(factory)));
    }

    @Test
    public void shouldReturnProjectsWhenValid() {
        when(projectRepository.findAll()).thenReturn(ProjectSeeds.getProjects());

        var result = pipeline.send(new GetProjectsQuery());

        expect.serializer("json").toMatchSnapshot(result);
    }
}
