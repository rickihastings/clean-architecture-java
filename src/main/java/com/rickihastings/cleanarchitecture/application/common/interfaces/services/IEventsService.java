package com.rickihastings.cleanarchitecture.application.common.interfaces.services;

import an.awesome.pipelinr.Notification;

public interface IEventsService {
    <E extends Notification> void publish(E event);
}
