package com.rickihastings.cleanarchitecture.web.controllers;

import an.awesome.pipelinr.Pipeline;

public abstract class ApiControllerBase {

    public final Pipeline pipeline;

    protected ApiControllerBase(Pipeline pipeline) {
        this.pipeline = pipeline;
    }
}
