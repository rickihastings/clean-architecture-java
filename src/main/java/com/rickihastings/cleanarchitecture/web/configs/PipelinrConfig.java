package com.rickihastings.cleanarchitecture.web.configs;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Notification;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.rickihastings.cleanarchitecture.application.common.middleware.AuthenticationMiddleware;
import com.rickihastings.cleanarchitecture.application.common.middleware.ValidationMiddleware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration
public class PipelinrConfig {

    @Autowired
    private AuthenticationMiddleware authenticationMiddleware;

    @Autowired
    private ValidationMiddleware validationMiddleware;

    @Bean
    Pipeline pipeline(ObjectProvider<Command.Handler> commandHandlers,
                      ObjectProvider<Notification.Handler> notificationHandlers,
                      ObjectProvider<Command.Middleware> middlewares)
    {
        return new Pipelinr()
            .with(commandHandlers::stream)
            .with(notificationHandlers::stream)
            .with(() -> Stream.of(authenticationMiddleware, validationMiddleware));
    }
}

