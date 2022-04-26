package com.rickihastings.cleanarchitecture.infrastructure.services;

import an.awesome.pipelinr.Notification;
import an.awesome.pipelinr.Pipeline;
import com.rickihastings.cleanarchitecture.application.common.interfaces.services.IEventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventsService implements IEventsService {

    private final Pipeline pipeline;

    @Override
    public <E extends Notification> void publish(E event) {
        pipeline.send(event);
    }
}
