package com.rickihastings.cleanarchitecture.application.eventhandlers;

import an.awesome.pipelinr.Notification;
import com.rickihastings.cleanarchitecture.domain.events.ProjectDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectDeletedEventHandler implements Notification.Handler<ProjectDeletedEvent> {

    private final Logger logger;

    @Override
    public void handle(ProjectDeletedEvent notification) {
        logger.info(String.format("Domain Event: %s", notification.item.toString()));
    }
}
