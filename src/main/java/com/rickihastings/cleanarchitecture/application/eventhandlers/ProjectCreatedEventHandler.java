package com.rickihastings.cleanarchitecture.application.eventhandlers;

import an.awesome.pipelinr.Notification;
import com.rickihastings.cleanarchitecture.domain.events.ProjectCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectCreatedEventHandler implements Notification.Handler<ProjectCreatedEvent> {

    private final Logger logger;

    @Override
    public void handle(ProjectCreatedEvent notification) {
        logger.info(String.format("Domain Event: %s", notification.item.toString()));
    }
}
