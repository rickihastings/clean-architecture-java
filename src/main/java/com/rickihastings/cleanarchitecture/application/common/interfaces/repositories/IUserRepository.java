package com.rickihastings.cleanarchitecture.application.common.interfaces.repositories;

import com.rickihastings.cleanarchitecture.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository {
    Optional<User> getUserByUsername(String username);
    User save(User save);
}
