package com.rickihastings.cleanarchitecture.application.common.middleware;

import an.awesome.pipelinr.Command;
import com.rickihastings.cleanarchitecture.application.common.exceptions.ValidationException;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Component
public class ValidationMiddleware implements Command.Middleware {

    private final Validator validator;

    public ValidationMiddleware(ValidatorFactory factory) {
        this.validator = factory.getValidator();
    }

    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {
        Set<ConstraintViolation<C>> violations = validator.validate(command);

        if (violations.size() > 0) {
            Collection<Throwable> exceptions = new ArrayList<>();
            for (ConstraintViolation<C> violation : violations) {
                exceptions.add(new Exception(violation.getMessage()));
            }

            throw new ValidationException(exceptions);
        }

        return next.invoke();
    }
}
