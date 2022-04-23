package com.rickihastings.cleanarchitecture.web.handlers;

import com.rickihastings.cleanarchitecture.application.common.exceptions.NotFoundException;
import com.rickihastings.cleanarchitecture.application.common.exceptions.UnauthorizedException;
import com.rickihastings.cleanarchitecture.application.common.exceptions.ValidationException;
import com.rickihastings.cleanarchitecture.web.errors.ApiError;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponse(HttpStatus status,
                                                 String title,
                                                 String path,
                                                 String type,
                                                 String error)
    {
        ApiError apiError = new ApiError(status, title, path, type, error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status,
                                                 String title,
                                                 String path,
                                                 String type,
                                                 List<String> errors)
    {
        ApiError apiError = new ApiError(status, title, path, type, errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /** Authentication Exceptions */
    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<Object> handleBadCredentialsException(WebRequest request) {
        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Unauthorized",
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                "https://datatracker.ietf.org/doc/html/rfc7235#section-3.1",
                "Unauthorized"
        );
    }

    @ExceptionHandler({ UnauthorizedException.class })
    public ResponseEntity<Object> handleUnauthorizedException(WebRequest request) {
        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Unauthorized",
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                "https://datatracker.ietf.org/doc/html/rfc7235#section-3.1",
                "Unauthorized"
        );
    }

    /** Not Found Exceptions */
    @NotNull
    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                @NotNull HttpHeaders headers,
                                                                @NotNull HttpStatus status,
                                                                @NotNull WebRequest request)
    {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "The specified resource was not found",
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.4",
                ex.getMessage()
        );
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "The specified resource was not found",
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.4",
                ex.getMessage()
        );
    }

    /** Uncaught Exceptions */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleControllerException(Throwable ex, WebRequest request) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An error occurred while processing your request",
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                "https://datatracker.ietf.org/doc/html/rfc7231#section-6.6.1",
                ex.toString()
        );
    }

    /** Validation Exceptions */
    @ExceptionHandler({ ValidationException.class })
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "One or more validation errors have occurred",
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.1",
                ex.exceptions().stream().map(Throwable::getMessage).collect(Collectors.toList())
        );
    }
}
