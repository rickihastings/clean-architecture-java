package com.rickihastings.cleanarchitecture.application.eventhandlers;

import an.awesome.pipelinr.Notification;
import com.rickihastings.cleanarchitecture.domain.events.ProjectUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectUpdatedEventHandler implements Notification.Handler<ProjectUpdatedEvent> {

    private final Logger logger;

    @Override
    public void handle(ProjectUpdatedEvent notification) {
        logger.info(String.format("Domain Event: %s", notification.item.toString()));
    }
}
