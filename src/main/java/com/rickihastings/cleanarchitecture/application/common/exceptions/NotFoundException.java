package com.rickihastings.cleanarchitecture.application.common.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String error) {
        super(error);
    }
}
