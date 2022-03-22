package com.rickihastings.cleanarchitecture.web.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.List;

@Getter
public class ApiError {

    private final HttpStatus status;
    private final String title;
    private final List<String> errors;

    public ApiError(HttpStatus status, String title, List<String> errors) {
        super();
        this.status = status;
        this.title = title;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String title, String error) {
        super();
        this.status = status;
        this.title = title;
        errors = List.of(error);
    }
}
