package com.rickihastings.cleanarchitecture.application.common.exceptions;

import an.awesome.pipelinr.AggregateException;

import java.util.List;

public class UnauthorizedException extends AggregateException {
    public UnauthorizedException() {
        super(List.of(new Exception("Unauthorized")));
    }
}
