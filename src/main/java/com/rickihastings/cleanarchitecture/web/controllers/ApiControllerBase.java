package com.rickihastings.cleanarchitecture.web.controllers;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ApiControllerBase {
    public final Pipeline pipeline;
}
