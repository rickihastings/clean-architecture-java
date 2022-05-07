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
public class GetProjectTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IProjectRepository projectRepository;

    @Test
    public void givenPayloadIsValid_thenStatus200() throws Exception {
        var project = projectRepository.findAll().get(0);

        mvc.perform(get(String.format("/api/v1/projects/%d", project.getId())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(project.getId().intValue())))
                .andExpect(jsonPath("$.title").isString())
                .andExpect(jsonPath("$.createdAt").isString());
    }

    @Test
    public void givenPayloadIsInvalid_thenStatus404() throws Exception {
        mvc.perform(get(String.format("/api/v1/projects/%d", 999)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("The specified resource was not found")))
                .andExpect(jsonPath("$.error", is("1 exception(s)")));
    }
}
