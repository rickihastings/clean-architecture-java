package com.rickihastings.cleanarchitecture.web.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private final Date timestamp = new Date();
    private final int status;
    private String error;
    private List<String> errors;
    private final String message;
    private final String path;
    private final String type;

    public ApiError(HttpStatus status, String message, String path, String type, List<String> errors) {
        super();
        this.status = status.value();
        this.message = message;
        this.errors = errors;
        this.path = path;
        this.type = type;
    }

    public ApiError(HttpStatus status, String message, String path, String type, String error) {
        super();
        this.status = status.value();
        this.message = message;
        this.error = error;
        this.path = path;
        this.type = type;
    }
}
