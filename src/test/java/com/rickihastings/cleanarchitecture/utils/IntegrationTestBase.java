package com.rickihastings.cleanarchitecture.utils;

import an.awesome.pipelinr.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IProjectRepository;
import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IUserRepository;
import com.rickihastings.cleanarchitecture.seeds.ProjectSeeds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

public class IntegrationTestBase {

    @Autowired
    private EntityManager em;

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    IProjectRepository projectRepository;

    @Autowired
    IUserRepository userRepository;

    @BeforeTransaction
    public void setup() {
        new TransactionTemplate(txManager).execute(status -> {
            ProjectSeeds.getProjects().forEach(project -> {
                project.setId(null);

                em.persist(project.getUser());
                em.persist(project);
            });

            em.flush();

            projectRepository.findAll().forEach(project -> System.out.println(project.getId()));

            return null;
        });
    }

    @AfterTransaction
    public void cleanup() {
        new TransactionTemplate(txManager).execute(status -> {
            projectRepository.findAll().forEach(project -> em.remove(project));
            em.flush();

            userRepository.findAll().forEach(user -> em.remove(user));
            em.flush();

            return null;
        });
    }

    protected String convertToJson(Command command) throws Exception {
        var objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(command);
    }
}
