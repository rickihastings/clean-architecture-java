package com.rickihastings.cleanarchitecture.web.controllers;

import an.awesome.pipelinr.Pipeline;
import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class ApiControllerBase {
    public final Pipeline pipeline;

    protected ApiControllerBase(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @RequestMapping("/**")
    public String handlerNotMappingRequest() throws NotFoundException {
        throw new NotFoundException("No handler mapping found.");
    }
}
