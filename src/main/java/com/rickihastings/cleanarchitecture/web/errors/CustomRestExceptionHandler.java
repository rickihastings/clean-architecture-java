package com.rickihastings.cleanarchitecture.web.errors;

import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import com.rickihastings.cleanarchitecture.application.common.exceptions.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponse(HttpStatus status, String title, String error) {
        ApiError apiError = new ApiError(status, title, error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String title, List<String> errors) {
        ApiError apiError = new ApiError(status, title, errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleControllerException(Throwable ex) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred.",
                ex.toString()
        );
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException( NotFoundException ex) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Page not found.",
                ex.toString()
        );
    }

    @ExceptionHandler({ ValidationException.class })
    public ResponseEntity<Object> handleAll(ValidationException ex) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "One or more validation errors have occurred.",
                ex.exceptions().stream().map(Throwable::getMessage).collect(Collectors.toList())
        );
    }
}
