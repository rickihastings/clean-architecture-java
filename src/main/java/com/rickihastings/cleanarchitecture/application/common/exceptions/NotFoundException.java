package com.rickihastings.cleanarchitecture.application.common.exceptions;

import an.awesome.pipelinr.AggregateException;

import java.util.List;

public class NotFoundException extends AggregateException {
    public NotFoundException(String error) {
        super(List.of(new Exception(error)));
    }
}
