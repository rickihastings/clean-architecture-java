package com.rickihastings.cleanarchitecture.application.common.exceptions;

import an.awesome.pipelinr.AggregateException;

import java.util.Collection;

public class ValidationException extends AggregateException {
    public ValidationException(Collection<Throwable> exceptions) {
        super(exceptions);
    }
}
