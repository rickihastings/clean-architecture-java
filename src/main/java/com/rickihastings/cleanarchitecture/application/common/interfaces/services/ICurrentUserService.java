package com.rickihastings.cleanarchitecture.application.common.interfaces.services;

import com.rickihastings.cleanarchitecture.domain.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface ICurrentUserService {
    User getUser();
}
