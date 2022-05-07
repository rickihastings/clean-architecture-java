package com.rickihastings.cleanarchitecture.web.controllers.v1.projects;

import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.utils.IntegrationTestBase;
import com.rickihastings.cleanarchitecture.web.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:integrationtest.properties")
@Transactional
public class GetProjectsTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IProjectRepository projectRepository;

    @Test
    public void givenPayloadIsValid_thenStatus200() throws Exception {
        var projects = projectRepository.findAll();

        mvc.perform(get("/api/v1/projects").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id", is(projects.get(0).getId().intValue())))
                .andExpect(jsonPath("$.[0].title", is(projects.get(0).getTitle())))
                .andExpect(jsonPath("$.[0].createdAt", is(projects.get(0).getCreatedAt().toString())))
                .andExpect(jsonPath("$.[0].createdBy", is(projects.get(0).getUser().getName())));
    }
}
