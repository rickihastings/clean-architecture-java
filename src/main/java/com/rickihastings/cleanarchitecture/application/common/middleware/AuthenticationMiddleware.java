package com.rickihastings.cleanarchitecture.application.common.middleware;

import an.awesome.pipelinr.Command;
import com.rickihastings.cleanarchitecture.application.common.annotations.Authenticate;
import com.rickihastings.cleanarchitecture.application.common.exceptions.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMiddleware implements Command.Middleware {

    @Override
    public <R, C extends Command<R>> R invoke(C command, Next<R> next) {
        var hasAuthenticate = command.getClass().isAnnotationPresent(Authenticate.class);

        if (hasAuthenticate) {
            var auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                return next.invoke();
            }

            throw new UnauthorizedException();
        }

        return next.invoke();
    }
}
