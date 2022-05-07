package com.rickihastings.cleanarchitecture.web.controllers.v1.projects;

import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.projects.commands.updateproject.UpdateProjectCommand;
import com.rickihastings.cleanarchitecture.utils.IntegrationTestBase;
import com.rickihastings.cleanarchitecture.web.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:integrationtest.properties")
@Transactional
public class UpdateProjectTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IProjectRepository projectRepository;

    @Test
    @WithUserDetails(value = "joebloggs", userDetailsServiceBeanName = "userDetailsService")
    public void givenPayloadIsValid_thenStatus200() throws Exception {
        var project = projectRepository.findAll().get(0);
        var title = "Integration Test Project";
        var payload = new UpdateProjectCommand();
        payload.setId(project.getId());
        payload.setTitle(title);

        mvc.perform(
                        post(String.format("/api/v1/projects/%d", project.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(payload))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(project.getId().intValue())))
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.createdAt").isString())
                .andExpect(jsonPath("$.updatedAt").isString());
    }

    @Test
    @WithUserDetails(value = "joebloggs", userDetailsServiceBeanName = "userDetailsService")
    public void givenPayloadIsInvalid_thenStatus400() throws Exception {
        var project = projectRepository.findAll().get(0);
        var id = project.getId();
        var payload = new UpdateProjectCommand();
        payload.setId(id);
        payload.setTitle(null);

        mvc.perform(
                        post(String.format("/api/v1/projects/%d", id))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(payload))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.errors", hasItem("title cannot be null")))
                .andExpect(jsonPath("$.errors", hasItem("title cannot be blank")));
    }

    @Test
    public void givenUserIsAnonymous_thenStatus401() throws Exception {
        mvc.perform(
                        post("/api/v1/projects")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertToJson(new UpdateProjectCommand()))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(401)))
                .andExpect(jsonPath("$.message", is("Unauthorized")))
                .andExpect(jsonPath("$.error", is("Unauthorized")));
    }
}
