package com.rickihastings.cleanarchitecture.infrastructure.services;

import com.rickihastings.cleanarchitecture.application.common.interfaces.services.ICurrentUserService;
import com.rickihastings.cleanarchitecture.domain.entities.User;
import com.rickihastings.cleanarchitecture.domain.entities.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService implements ICurrentUserService {

    @Override
    public User getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (UserDetails) authentication.getPrincipal();

        return userDetails.getUser();
    }
}
