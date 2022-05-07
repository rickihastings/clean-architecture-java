package com.rickihastings.cleanarchitecture.application.common.interfaces.repositories;

import com.rickihastings.cleanarchitecture.domain.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository {
    List<User> findAll();
    Optional<User> getUserByUsername(String username);
    User save(User save);
}
